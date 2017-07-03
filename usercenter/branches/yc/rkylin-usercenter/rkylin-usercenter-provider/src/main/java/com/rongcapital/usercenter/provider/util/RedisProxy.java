package com.rongcapital.usercenter.provider.util;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.rongcapital.redis.sdr.base.BaseRedisService;
import com.rongcapital.usercenter.api.vo.AuthResultVo;
import com.rongcapital.usercenter.api.vo.LoginInfo.TerminalType;
import com.rongcapital.usercenter.provider.po.UserInfo;
import com.rongcapital.usercenter.provider.vo.RedisAuthInfo;

@Component
public class RedisProxy {

    

    
    private static final Logger logger = LoggerFactory.getLogger(UserRedisSessionUtils.class);

    private static  RedisTemplate<String,Object> redisTemplate;
    
    private static BaseRedisService<String, Object> baseRedisService;
    
    
    private static String redisLoginPrefix = "sso_login";
    
    private static String nameAndTokenPrefix = "sso_token";
   
    private static String cassCheckPrefix = "uc_caascheck";
    
    private static final String SEPARATOR = "_";
   
    private static final int PWDERROR_EXPIRE_TIME = 86400;//密码失效时间 24小时
    
    
    
    
    @Autowired
    public  void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisProxy.redisTemplate = redisTemplate;
    }
    
    @Autowired
    public  void setBaseRedisService(BaseRedisService<String, Object> baseRedisService) {
        RedisProxy.baseRedisService = baseRedisService;
    }



    public  int getRedisTerminalType(TerminalType terminalType){
        return  terminalType.value();
    }
    
    public  String getRedisUserLoginKey(String appId,TerminalType terminalType,String userToken){
        int redisTerminalType = getRedisTerminalType(terminalType);
        String redisUserLoginKey = redisLoginPrefix+SEPARATOR+userToken+SEPARATOR+ redisTerminalType;
        return redisUserLoginKey;
    }
    
    public  String[] getRedisUserAllLoginKey(String appId,String userToken){
        String redisUserLoginKey1 = redisLoginPrefix+SEPARATOR+userToken+SEPARATOR+ TerminalType.PC.value();
        String redisUserLoginKey2 = redisLoginPrefix+SEPARATOR+userToken+SEPARATOR+ TerminalType.ISO.value();
        String redisUserLoginKey3 = redisLoginPrefix+SEPARATOR+userToken+SEPARATOR+ TerminalType.ANDROID.value();
        return new String[]{redisUserLoginKey1,redisUserLoginKey2,redisUserLoginKey3};
    }
    
    
    
    public  String getRedisUserSessionKey(String userToken){
        String redisUserSessionKey = redisLoginPrefix +SEPARATOR+ userToken;
        return redisUserSessionKey;
    }

    
    
    @Transactional
    public  void doUserLoginInfo(final String appCode,TerminalType terminalType,String userToken,final UserInfo userInfo,AuthResultVo authInfo) throws Exception{
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
    
    public  UserInfo getUserSession(String userToken) throws Exception{
        StopWatch sw = new StopWatch("getUserSession");
        sw.start("用户中心获取用户信息开始");
        String redisUserSessionKey = getRedisUserSessionKey(userToken);
        UserInfo userInfo = (UserInfo) baseRedisService.get(redisUserSessionKey);
        sw.stop();
        logger.info(sw.prettyPrint());
        return userInfo;
    }
    
    public   void  updateUserSession(String userToken,UserInfo  userInfo) throws Exception{
        String redisUserSessionKey = getRedisUserSessionKey(userToken);
        int expireTime =redisTemplate.boundValueOps(redisUserSessionKey).getExpire().intValue();
        baseRedisService.set(redisUserSessionKey, userInfo, expireTime);
       
    }
    
    
    public  RedisAuthInfo getAuthInfo(String appId,String userToken){
        String[] redisUserLoginKeys = getRedisUserAllLoginKey(appId, userToken);
        for (String redisUserLoginKey : redisUserLoginKeys) {
                RedisAuthInfo AuthResultVo = (RedisAuthInfo) redisTemplate.boundHashOps(redisUserLoginKey).get(appId);
                if(null != AuthResultVo){
                    return AuthResultVo;
                }
        }
        return null;
    }
    
    public  RedisAuthInfo getAuthInfoWithTerminalType(String appId,String userToken,TerminalType terminalType){
        String redisUserLoginKey = getRedisUserLoginKey(appId, terminalType, userToken);
        RedisAuthInfo AuthResultVo = (RedisAuthInfo) redisTemplate.boundHashOps(redisUserLoginKey).get(appId);
        return AuthResultVo;
    }
    
    public  void doRefleshUserInfo(String appId,String userToken,TerminalType terminalType,AuthResultVo newAuthInfo) throws Exception{
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
    
    public  void doAuthInfo(String appId,String userToken,TerminalType terminalType , AuthResultVo authInfo) throws Exception{
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
    
    
    public  void doLogout(String appId,String userToken,TerminalType terminalType) throws Exception{
        String redisUserLoginKey = getRedisUserLoginKey(appId, terminalType, userToken);
        baseRedisService.delete(redisUserLoginKey);
    }
    
    //删除应用对应  accessToken
    public  void delAccessTokenByAppCode(String appId,String userToken,TerminalType terminalType){
        
        String redisUserLoginKey = getRedisUserLoginKey(appId, terminalType, userToken);
        redisTemplate.boundHashOps(redisUserLoginKey).delete(appId);
        
    }
    
    //通过终端号 获取redis 对应所有userToken accessToke值
    
    public  Map<String,RedisAuthInfo> getRedisAuthInfoList(String userToken,TerminalType terminalType){
        
        
        String loginKey=getRedisUserLoginKey("", terminalType, userToken);
        
        BoundHashOperations<String, String, RedisAuthInfo> boundHashOps = redisTemplate.boundHashOps(loginKey);
        
        Map<String, RedisAuthInfo> entries = boundHashOps.entries();
        
        return entries;
    }
   
    public   void delUserSession(String userToken) throws Exception{
        String redisUserSessionKey = getRedisUserSessionKey(userToken);
        baseRedisService.delete(redisUserSessionKey);
        
    }
    private  final String REDIS_XAUTHTOKEN_PREFIXKEY = "sso_xauthtoken_";
    public  String getRedisXAuthTokenKey(String loginName,String orgCode){
        return REDIS_XAUTHTOKEN_PREFIXKEY+loginName+SEPARATOR+orgCode;
    }
    
    public  void doRefleshXAuthToken(String loginName,String orgCode,String xAuthToken) throws Exception{
        if(StringUtils.isNotEmpty(xAuthToken)){
            String redisXAuthTokenKey = getRedisXAuthTokenKey(loginName,orgCode);
           baseRedisService.set(redisXAuthTokenKey, xAuthToken, PWDERROR_EXPIRE_TIME);
        }
    }
    public  String  getXAuthToken(String loginName,String orgCode) throws Exception{
        String redisXAuthTokenKey=getRedisXAuthTokenKey(loginName,orgCode);
        if(baseRedisService.hasKey(redisXAuthTokenKey)){
           return baseRedisService.get(redisXAuthTokenKey).toString();
        }
        return  null;
    }
    public   void  delXAuthToken(String loginName,String orgCode) throws Exception {
        String redisXAuthTokenKey=getRedisXAuthTokenKey(loginName,orgCode);
        baseRedisService.delete(redisXAuthTokenKey);
    }
    
    public  boolean isExsitErrorLoginTime(String loginName,String orgCode) throws Exception{
        String errorLoginKey="UCLOGIN"+loginName+orgCode;
        return baseRedisService.hasKey(errorLoginKey);
    }
    
    public  void setErrorLoginTime(String loginName,String orgCode,int count,int expireTime) throws Exception{
        String errorLoginKey="UCLOGIN"+loginName+orgCode;
        if(baseRedisService.hasKey(errorLoginKey)){
            expireTime= redisTemplate.boundValueOps(errorLoginKey).getExpire().intValue();
        }
        baseRedisService.set(errorLoginKey, count, expireTime);
      //  redisTemplate.boundValueOps(errorLoginKey).set(count);
        
    }
    public   String  getErrorLoginTime(String loginName,String orgCode) throws Exception{
        String errorLoginKey="UCLOGIN"+loginName+orgCode;
        return baseRedisService.get(errorLoginKey).toString();
    }
   
    /**
     * 系统参数是否存在
     * 
     */
    public  boolean isSystemConfigKeyExsit(String key) throws Exception{
        return baseRedisService.hasKey( key);
    }
    /**
     * 
     * Discription:设置UC系统配置，放进redis避免每次查库,设置过期时间为10天
     * @since 2016年12月23日
     */
    public  void setSystemConfigRedis(String key,String value) throws Exception{
        baseRedisService.set(key, value, 10*24*60*60);
        
    }
    /**
     * 
     * Discription:获取系统参数
 
     * @since 2016年12月23日
     */
    public  String getSystemConfigRedis(String key) throws Exception{
        return baseRedisService.get(key).toString();
        
    }
    public  void setLoginKeyExpireTime(String loginName,String orgCode,int expireTime){
        String errorLoginKey="UCLOGIN"+loginName+orgCode;
        redisTemplate.boundSetOps(errorLoginKey).expire(expireTime, TimeUnit.SECONDS);
        
    }
    public  boolean isLoginKeyExsit(String loginName,String orgCode) throws Exception{
        String errorLoginKey="UCLOGIN"+loginName+orgCode;
        return baseRedisService.hasKey(errorLoginKey);
    }
    public  void  delLoginKey(String loginName,String orgCode) throws Exception{
        String errorLoginKey="UCLOGIN"+loginName+orgCode;
        baseRedisService.delete(errorLoginKey);
    }
    public  void setAccessTicket(String loginName,String orgCode,String value,int expireTime) throws Exception{
        String accessTicket="UCTICKET"+loginName+orgCode;
        baseRedisService.set(accessTicket, value, expireTime);
    }
    public  String getAccessTicket(String loginName,String orgCode) throws Exception{
        String accessTicket="UCTICKET"+loginName+orgCode;
       return baseRedisService.get(accessTicket).toString();
    }
    /**
     * 保存登陆名和userToken关系
     * @param loginName
     * @param orgCode
     * @param userToken
     * @throws Exception 
     */
    public  void setUserNameAndUserToken(String loginName,String orgCode,String userToken,int expireTime) throws Exception{
        String nameAndToken=nameAndTokenPrefix+SEPARATOR+loginName+SEPARATOR+orgCode;
        //redisTemplate.boundValueOps(nameAndToken).set(userToken);
        baseRedisService.set(nameAndToken, userToken, expireTime);
        
    }
    public  String getUserTokenByName(String loginName,String orgCode) throws Exception{
        String nameAndToken=nameAndTokenPrefix+SEPARATOR+loginName+SEPARATOR+orgCode;
        if(baseRedisService.hasKey(nameAndToken)){
           return baseRedisService.get(nameAndToken).toString();        
        }else{
            return null;
        }
    }
    /**
     * 重新登陆删除旧的 userToken相关信息
     * @param loginName
     * @param orgCode
     * @param terminalType
     * @throws Exception
     */
    public  void delUserTokenByNameAndTerminal(String loginName,String orgCode,TerminalType terminalType) throws Exception{
        String userToken=getUserTokenByName(loginName, orgCode);
        String redisUserSessionKey = getRedisUserSessionKey(userToken);
        String redisUserLoginKey = getRedisUserLoginKey("", terminalType, userToken);
        if(baseRedisService.hasKey(redisUserSessionKey)){
            baseRedisService.delete(redisUserSessionKey);
        }
        if(baseRedisService.hasKey(redisUserLoginKey)){
            baseRedisService.delete(redisUserLoginKey);
        }
    }
    
    //设置caas checkResult结果
    public   void  setCaasCheckResult(String userToken,int expireTime) throws Exception{
        String caasCheckResultKey=cassCheckPrefix+SEPARATOR+userToken;
        baseRedisService.set(caasCheckResultKey, userToken, expireTime);
    }
    //判断caas 结果是否存在
    public   boolean  isCaasCheckResult(String userToken) throws Exception {
        String caasCheckResultKey=cassCheckPrefix+SEPARATOR+userToken;
        return baseRedisService.hasKey(caasCheckResultKey);
    }
}
