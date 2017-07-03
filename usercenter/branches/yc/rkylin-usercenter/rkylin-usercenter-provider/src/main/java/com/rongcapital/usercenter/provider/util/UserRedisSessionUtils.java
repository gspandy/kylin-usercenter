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
public class UserRedisSessionUtils {

    private static RedisProxy redisProxy;

    
   
    @Autowired
    public  void setRedisProxy(RedisProxy redisProxy) {
        UserRedisSessionUtils.redisProxy = redisProxy;
    }


    public static int getRedisTerminalType(TerminalType terminalType) {
        return redisProxy.getRedisTerminalType(terminalType);
    }

    public static String getRedisUserLoginKey(String appId, TerminalType terminalType, String userToken) {
        return redisProxy.getRedisUserLoginKey(appId, terminalType, userToken);
    }

    public static String[] getRedisUserAllLoginKey(String appId, String userToken) {
        return redisProxy.getRedisUserAllLoginKey(appId, userToken);
    }

    public static String getRedisUserSessionKey(String userToken) {
        return redisProxy.getRedisUserSessionKey(userToken);
    }

    @Transactional
    public static void doUserLoginInfo(final String appCode, TerminalType terminalType, String userToken,
            final UserInfo userInfo, AuthResultVo authInfo) throws Exception {
        redisProxy.doUserLoginInfo(appCode, terminalType, userToken, userInfo, authInfo);
    }

    public static UserInfo getUserSession(String userToken) throws Exception {
        return redisProxy.getUserSession(userToken);
    }

    public static void updateUserSession(String userToken, UserInfo userInfo) throws Exception {
        redisProxy.updateUserSession(userToken, userInfo);
    }

    public static RedisAuthInfo getAuthInfo(String appId, String userToken) {
        return redisProxy.getAuthInfo(appId, userToken);
    }

    public static RedisAuthInfo getAuthInfoWithTerminalType(String appId, String userToken, TerminalType terminalType) {
        return redisProxy.getAuthInfoWithTerminalType(appId, userToken, terminalType);
    }

    public static void doRefleshUserInfo(String appId, String userToken, TerminalType terminalType,
            AuthResultVo newAuthInfo) throws Exception {
        redisProxy.doRefleshUserInfo(appId, userToken, terminalType, newAuthInfo);
    }

    public static void doLogout(String appId, String userToken, TerminalType terminalType) throws Exception {
        redisProxy.doLogout(appId, userToken, terminalType);
    }

    // 删除应用对应 accessToken
    public static void delAccessTokenByAppCode(String appId, String userToken, TerminalType terminalType) {

        redisProxy.delAccessTokenByAppCode(appId, userToken, terminalType);
    }

    // 通过终端号 获取redis 对应所有userToken accessToke值

    public static Map<String, RedisAuthInfo> getRedisAuthInfoList(String userToken, TerminalType terminalType) {
        return redisProxy.getRedisAuthInfoList(userToken, terminalType);
    }

    public static void delUserSession(String userToken) throws Exception {
        redisProxy.delUserSession(userToken);

    }

    public static String getRedisXAuthTokenKey(String loginName, String orgCode) {
        return redisProxy.getRedisXAuthTokenKey(loginName, orgCode);
    }

    public static void doRefleshXAuthToken(String loginName, String orgCode, String xAuthToken) throws Exception {
        redisProxy.doRefleshXAuthToken(loginName, orgCode, xAuthToken);
    }

    public static String getXAuthToken(String loginName, String orgCode) throws Exception {
        return redisProxy.getXAuthToken(loginName, orgCode);
    }

    public static void delXAuthToken(String loginName, String orgCode) throws Exception {
        redisProxy.delXAuthToken(loginName, orgCode);
    }

    public static boolean isExsitErrorLoginTime(String loginName, String orgCode) throws Exception {
        return redisProxy.isExsitErrorLoginTime(loginName, orgCode);
    }

    public static void setErrorLoginTime(String loginName, String orgCode, int count, int expireTime) throws Exception {
        redisProxy.setErrorLoginTime(loginName, orgCode, count, expireTime);
    }

    public static String getErrorLoginTime(String loginName, String orgCode) throws Exception {
       
        return redisProxy.getErrorLoginTime(loginName, orgCode);
    }

    /**
     * 系统参数是否存在
     * 
     */
    public static boolean isSystemConfigKeyExsit(String key) throws Exception {
        return redisProxy.isSystemConfigKeyExsit(key);
    }

    /**
     * 
     * Discription:设置UC系统配置，放进redis避免每次查库,设置过期时间为10天
     * 
     * @since 2016年12月23日
     */
    public static void setSystemConfigRedis(String key, String value) throws Exception {
            redisProxy.setSystemConfigRedis(key, value);

    }

    /**
     * 
     * Discription:获取系统参数
     * 
     * @since 2016年12月23日
     */
    public static String getSystemConfigRedis(String key) throws Exception {
        return redisProxy.getSystemConfigRedis(key);

    }

    public static void setLoginKeyExpireTime(String loginName, String orgCode, int expireTime) {
        redisProxy.setLoginKeyExpireTime(loginName, orgCode, expireTime);
    }

    public static boolean isLoginKeyExsit(String loginName, String orgCode) throws Exception {
        return redisProxy.isLoginKeyExsit(loginName, orgCode);
    }

    public static void delLoginKey(String loginName, String orgCode) throws Exception {
       redisProxy.delLoginKey(loginName, orgCode);
    }

    public static void setAccessTicket(String loginName, String orgCode, String value, int expireTime) throws Exception {
        redisProxy.setAccessTicket(loginName, orgCode, value, expireTime);
    }

    public static String getAccessTicket(String loginName, String orgCode) throws Exception {
        return redisProxy.getAccessTicket(loginName, orgCode);
    }

    /**
     * 保存登陆名和userToken关系
     * 
     * @param loginName
     * @param orgCode
     * @param userToken
     * @throws Exception
     */
    public static void setUserNameAndUserToken(String loginName, String orgCode, String userToken, int expireTime)
            throws Exception {
        redisProxy.setUserNameAndUserToken(loginName, orgCode, userToken, expireTime);
    }

    public static String getUserTokenByName(String loginName, String orgCode) throws Exception {
        return redisProxy.getUserTokenByName(loginName, orgCode);
    }

    /**
     * 重新登陆删除旧的 userToken相关信息
     * 
     * @param loginName
     * @param orgCode
     * @param terminalType
     * @throws Exception
     */
    public static void delUserTokenByNameAndTerminal(String loginName, String orgCode, TerminalType terminalType)
            throws Exception {
       redisProxy.delUserTokenByNameAndTerminal(loginName, orgCode, terminalType);
    }
    //设置caas checkResult结果
    public static  void  setCaasCheckResult(String userToken,int expireTime) throws Exception{
        redisProxy.setCaasCheckResult(userToken, expireTime);
    }
    //判断caas 结果是否存在
    public static  boolean  isCaasCheckResult(String userToken) throws Exception {
        
        return redisProxy.isCaasCheckResult(userToken);
    }
}
