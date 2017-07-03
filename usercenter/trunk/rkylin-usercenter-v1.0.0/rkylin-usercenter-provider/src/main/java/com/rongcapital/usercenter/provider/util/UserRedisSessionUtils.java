package com.rongcapital.usercenter.provider.util;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rongcapital.redis.sdr.base.BaseRedisService;
import com.rongcapital.usercenter.api.vo.AuthResultVo;
import com.rongcapital.usercenter.api.vo.LoginInfo.TerminalType;
import com.rongcapital.usercenter.provider.po.UserInfo;
import com.rongcapital.usercenter.provider.vo.RedisAuthInfo;

@Component
public class UserRedisSessionUtils {
  
    private static  RedisTemplate<String,Object> redisTemplate;
    
    private static BaseRedisService<String, Object> baseRedisService;
    
    
    private static String redisLoginPrefix = "sso_login";
    
    private static final String SEPARATOR = "_";

    @Autowired
    public  void setRedisTemplate(RedisTemplate redisTemplate) {
        UserRedisSessionUtils.redisTemplate = redisTemplate;
    }
    
    @Autowired
    public  void setBaseRedisService(BaseRedisService<String, Object> baseRedisService) {
        UserRedisSessionUtils.baseRedisService = baseRedisService;
    }



    public static int getRedisTerminalType(TerminalType terminalType){
        return  terminalType.value();
    }
    
    public static String getRedisUserLoginKey(String appId,TerminalType terminalType,String userToken){
        int redisTerminalType = getRedisTerminalType(terminalType);
        String redisUserLoginKey = redisLoginPrefix+SEPARATOR+userToken+SEPARATOR+ redisTerminalType;
        return redisUserLoginKey;
    }
    
    public static String[] getRedisUserAllLoginKey(String appId,String userToken){
        String redisUserLoginKey1 = redisLoginPrefix+SEPARATOR+userToken+SEPARATOR+ TerminalType.PC.value();
        String redisUserLoginKey2 = redisLoginPrefix+SEPARATOR+userToken+SEPARATOR+ TerminalType.ISO.value();
        String redisUserLoginKey3 = redisLoginPrefix+SEPARATOR+userToken+SEPARATOR+ TerminalType.ANDROID.value();
        return new String[]{redisUserLoginKey1,redisUserLoginKey2,redisUserLoginKey3};
    }
    
    
    
    public static String getRedisUserSessionKey(String userToken){
        String redisUserSessionKey = redisLoginPrefix +SEPARATOR+ userToken;
        return redisUserSessionKey;
    }

    
    
    @Transactional
    public static void doUserLoginInfo(final String appCode,TerminalType terminalType,String userToken,final UserInfo userInfo,AuthResultVo authInfo) throws Exception{
        int expires = authInfo.getExpiresIn();
        final String redisUserLoginKey = getRedisUserLoginKey(appCode, terminalType, userToken);
        final String redisUserSessionKey = getRedisUserSessionKey(userToken);
        
        
        final RedisAuthInfo redisAuthInfo = new RedisAuthInfo().valueOf(authInfo);
        //authInfo 目前每个设备终端保存自己的session
        RedisAuthInfo oldAuthInfo = getAuthInfo(appCode, userToken);
        int maxExpires = expires;
        if(null != oldAuthInfo){
            maxExpires = Math.max(expires, oldAuthInfo.getExpiresIn());
        }
        
        //保存用户最大失效时间
        userInfo.setMaxExpireTime(maxExpires);
       //   redis transaction 1
        final RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        final RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
        redisTemplate.execute(new RedisCallback<Void>() {

            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                
                byte[]  userSessionKey = keySerializer.serialize(redisUserSessionKey);
                byte[]  userLoginKey = keySerializer.serialize(redisUserLoginKey);
                
                connection.multi();
                //session
                
                
                connection.set(userSessionKey, valueSerializer.serialize(userInfo));
                //accessToken
                connection.hSet(userLoginKey, keySerializer.serialize(appCode), valueSerializer.serialize(redisAuthInfo));
                int maxExpiresIn = userInfo.getMaxExpireTime();
                connection.expire(userSessionKey, maxExpiresIn);
                connection.expire(userLoginKey, maxExpiresIn);
                connection.exec();
                return null;
            }
        });
        
        
        
//        redisTemplate.boundValueOps(redisUserSessionKey).set(userInfo, maxExpires, TimeUnit.SECONDS);
//        baseRedisService.set(redisUserSessionKey,userInfo , maxExpires);
//        redisTemplate.boundHashOps(redisUserLoginKey).put(appId, redisAuthInfo);
//        redisTemplate.boundHashOps(redisUserLoginKey).expire(maxExpires, TimeUnit.SECONDS);
    }
    
    public static UserInfo getUserSession(String userToken) throws Exception{
        String redisUserSessionKey = getRedisUserSessionKey(userToken);
        UserInfo userInfo = (UserInfo) baseRedisService.get(redisUserSessionKey);
        return userInfo;
    }
    
    public static RedisAuthInfo getAuthInfo(String appId,String userToken){
        String[] redisUserLoginKeys = getRedisUserAllLoginKey(appId, userToken);
        for (String redisUserLoginKey : redisUserLoginKeys) {
                RedisAuthInfo AuthResultVo = (RedisAuthInfo) redisTemplate.boundHashOps(redisUserLoginKey).get(appId);
                if(null != AuthResultVo){
                    return AuthResultVo;
                }
        }
        return null;
    }
    
    public static RedisAuthInfo getAuthInfoWithTerminalType(String appId,String userToken,TerminalType terminalType){
        String redisUserLoginKey = getRedisUserLoginKey(appId, terminalType, userToken);
        RedisAuthInfo AuthResultVo = (RedisAuthInfo) redisTemplate.boundHashOps(redisUserLoginKey).get(appId);
        return AuthResultVo;
    }
    
    public static void doRefleshUserInfo(String appId,String userToken,TerminalType terminalType,AuthResultVo newAuthInfo) throws Exception{
        String redisUserLoginKey = getRedisUserLoginKey(appId, terminalType, userToken);
        String redisUserSessionKey = getRedisUserSessionKey(userToken);
        int expires = newAuthInfo.getExpiresIn();
        //替换accessToken
        //校验oldRedisAuthInfo不能为空
        RedisAuthInfo redisAuthInfo = new RedisAuthInfo().valueOf(newAuthInfo);
        UserInfo userSession = getUserSession(userToken);
        int maxExpiresIn = Math.max(expires, userSession.getMaxExpireTime());
        userSession.setMaxExpireTime(maxExpiresIn);
        redisTemplate.boundHashOps(redisUserLoginKey).put(appId, redisAuthInfo);
        redisTemplate.boundHashOps(redisUserLoginKey).expire(expires, TimeUnit.SECONDS);
       
        baseRedisService.set(redisUserSessionKey, userSession, expires);
        //重置session超时时间
        baseRedisService.expire(redisUserSessionKey,maxExpiresIn , TimeUnit.SECONDS);
    }
    
    public static void doAuthInfo(String appId,String userToken,TerminalType terminalType , AuthResultVo authInfo) throws Exception{
        //check userSession is Exists
        int expires = authInfo.getExpiresIn();
        
        
        String redisUserLoginKey = getRedisUserLoginKey(appId, terminalType, userToken);
        String redisUserSessionKey = getRedisUserSessionKey(userToken);
        UserInfo userSession = getUserSession(userToken);
        if(null == userSession){
            throw new Exception("用户不存在，请先登录");
        }
        
        
        RedisAuthInfo redisAuthInfo = new RedisAuthInfo().valueOf(authInfo);
        //authInfo 目前最多只有两个 移动设备和PC
        int maxExpires = Math.max(expires, userSession.getMaxExpireTime());
        userSession.setMaxExpireTime(maxExpires);
        
        baseRedisService.set(redisUserSessionKey, userSession, maxExpires);
        redisTemplate.boundHashOps(redisUserLoginKey).put(appId, redisAuthInfo);
        redisTemplate.boundHashOps(redisUserLoginKey).expire(maxExpires, TimeUnit.SECONDS);
        
    }
    
    
    public static void doLogout(String appId,String userToken,TerminalType terminalType) throws Exception{
        String redisUserLoginKey = getRedisUserLoginKey(appId, terminalType, userToken);
        baseRedisService.delete(redisUserLoginKey);
    }
    
    //删除应用对应  accessToken
    public static void delAccessTokenByAppCode(String appId,String userToken,TerminalType terminalType){
        
        String redisUserLoginKey = getRedisUserLoginKey(appId, terminalType, userToken);
        redisTemplate.boundHashOps(redisUserLoginKey).delete(appId);
        
    }
    
    //通过终端号 获取redis 对应所有userToken accessToke值
    
    public static Map<String,RedisAuthInfo> getRedisAuthInfoList(String userToken,TerminalType terminalType){
        
        
        String loginKey=getRedisUserLoginKey("", terminalType, userToken);
        
        BoundHashOperations<String, String, RedisAuthInfo> boundHashOps = redisTemplate.boundHashOps(loginKey);
        
        Map<String, RedisAuthInfo> entries = boundHashOps.entries();
        
        return entries;
    }
   
    public static  void delUserSession(String userToken) throws Exception{
        String redisUserSessionKey = getRedisUserSessionKey(userToken);
        baseRedisService.delete(redisUserSessionKey);
        
    }
    private static final String REDIS_XAUTHTOKEN_PREFIXKEY = "sso_xauthtoken_";
    public static String getRedisXAuthTokenKey(String loginName){
        return REDIS_XAUTHTOKEN_PREFIXKEY+loginName;
    }
    
    public static void doRefleshXAuthToken(String loginName,String xAuthToken) throws Exception{
        if(StringUtils.isNotEmpty(xAuthToken)){
            UserInfo userSession = getUserSession(loginName);
            String redisXAuthTokenKey = getRedisXAuthTokenKey(loginName);
            if(null == userSession){
                baseRedisService.set(redisXAuthTokenKey, xAuthToken, Integer.MAX_VALUE);
            }else{
                
                baseRedisService.set(redisXAuthTokenKey, xAuthToken, userSession.getMaxExpireTime());
            }
        }
    }
    public static String  getXAuthToken(String loginName) throws Exception{
        String redisXAuthTokenKey=getRedisXAuthTokenKey(loginName);
        return  baseRedisService.get(redisXAuthTokenKey).toString();
    }
    public  static void  delXAuthToken(String loginName) throws Exception {
        String redisXAuthTokenKey=getRedisXAuthTokenKey(loginName);
        baseRedisService.delete(redisXAuthTokenKey);
    }
}
