package com.rongcapital.usercenter.provider.service;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.rkylin.usercenter.util.CaasUserServiceProxy;
import com.rkylin.usercenter.util.JsonUtil;
import com.rongcapital.usercenter.api.po.ExtendUserInfo;
import com.rongcapital.usercenter.api.po.UserInfoPo;
import com.rongcapital.usercenter.api.service.UserCenterService;
import com.rongcapital.usercenter.api.vo.AuthResultVo;
import com.rongcapital.usercenter.api.vo.BaseResultVo;
import com.rongcapital.usercenter.api.vo.LoginInfo.TerminalType;
import com.rongcapital.usercenter.provider.constant.UserCenterErrorCode;
import com.rongcapital.usercenter.provider.dao.CompanyInfoMapper;
import com.rongcapital.usercenter.provider.dao.PersonInfoMapper;
import com.rongcapital.usercenter.provider.dao.UserInfoMapper;
import com.rongcapital.usercenter.provider.dao.UserLoginHistoryMapper;
import com.rongcapital.usercenter.provider.dao.UserLoginMapper;
import com.rongcapital.usercenter.provider.po.UserInfo;
import com.rongcapital.usercenter.provider.po.UserLoginHistory;
import com.rongcapital.usercenter.provider.util.CaasConfigPathUtils;
import com.rongcapital.usercenter.provider.util.UserCenterConfig;
import com.rongcapital.usercenter.provider.util.UserRedisSessionUtils;
import com.rongcapital.usercenter.provider.vo.RedisAuthInfo;
import com.ruixue.serviceplatform.commons.exception.NotFoundException;

@Service
@Configuration  
public class UserCenterServiceImpl implements UserCenterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCenterServiceImpl.class);

    private static final String compareTimePeriod = UserCenterConfig.getProperty("compare.time.period");
   
    @Autowired
    private CaasUserServiceProxy caasUserServiceProxy;

   
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private UserLoginHistoryMapper userLoginHistoryMapper;
    
    @Autowired
    private LocalUserService  localUserService;

    @Override
    public BaseResultVo<String> isLoginNameExists(String loginName, String appCode, String xAuthToken) {
        return caasUserServiceProxy.isLoginNameExists(this.getConfigfile(appCode), appCode, loginName, xAuthToken);
    }

    
    @Override
    public BaseResultVo<Boolean>  passwordReset(UserInfoPo userInfo,String newPwd, String appCode) {
        LOGGER.info("传入参数userInfo:{},newPwd:{},appCode:{}",JsonUtil.bean2JsonStr(userInfo), newPwd,appCode);
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        
        if (userInfo == null) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_ENTITY_EMPTY);
            resultVo.setErrorMsg("密码操作失败：用户实体为空");
            return resultVo;
        }

        if (StringUtils.isEmpty(appCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("应用appcode不能为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(newPwd)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_NEWPASSWORD_EMPTY);
            resultVo.setErrorMsg("密码操作失败：新密码传入为空");
            return resultVo;
        }
        try{
            this.getConfigfile(appCode);
        }
        catch(NotFoundException e){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_APPCODE);
            resultVo.setErrorMsg("应用appcode不存在");
            return resultVo;
        }
        String userName = userInfo.getUserName();
        if (StringUtils.isEmpty(userName)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("登录名不能为空");
            return resultVo;
        }
        try{
        UserInfo userInfoSelect = this.userInfoMapper.selectByUserId(userName);
        if(userInfoSelect==null){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_ERROR);
            resultVo.setErrorMsg("用户不存在");
            return resultVo;
        }
        
        // 重置
        resultVo = caasUserServiceProxy.resetPwd(this.getConfigfile(appCode), appCode, userName, newPwd);
        if(resultVo.isSuccess()){
            LOGGER.info("密码重置成功,userName:{},appCode:{},newPwd:{}",userName,appCode,newPwd);
            UserInfo  user= new UserInfo();
            user.setUserId(userName);
           try{
            localUserService.modifyPwd(user, newPwd);
           }catch(Exception e){
               LOGGER.error("密码重置失败",e);
           }
        }
        }
        catch(Exception e){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("程序执行异常"+e.getMessage());
            return resultVo; 
        }
        return resultVo;
    }

    @Override
    public BaseResultVo<String> passwordUpdate(String userToken, String oldPwd, String newPwd, String appCode,String vcode,String xAuthToken) {

        LOGGER.info("传入参数userToken:{},oldPwd:{},newPwd:{},appCode:{}" ,userToken,oldPwd ,newPwd,appCode);
        BaseResultVo<String> resultVo = new BaseResultVo<String>();
        if (StringUtils.isEmpty(userToken)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_TOKEN_EMPTY);
            resultVo.setErrorMsg("获取用户信息失败：用户token为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(oldPwd)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_OLDPASSWORD_EMPTY);
            resultVo.setErrorMsg("密码操作失败：老密码输入为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(newPwd)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_NEWPASSWORD_EMPTY);
            resultVo.setErrorMsg("密码操作失败：新密码传入为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(appCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("应用appcode不能为空");
            return resultVo;
        }
        String accessToken = this.getAccessToken(appCode, userToken);
        if(StringUtils.isEmpty(accessToken)){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOLOGIN_ERROR);
            resultVo.setErrorMsg("用户未登陆或者已注销");
            return resultVo;
        }
        String fileConfig="";
        try{
            fileConfig= this.getConfigfile(appCode);
        }
        catch(NotFoundException e){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_APPCODE);
            resultVo.setErrorMsg("应用appcode不存在");
            return resultVo;
        }
        
        resultVo= caasUserServiceProxy.updatePwd(fileConfig, appCode, accessToken, oldPwd, newPwd, vcode, xAuthToken);
        if(resultVo.isSuccess()){//修改密码成功，清除登陆信息，重新登陆
            LOGGER.info("密码修改成功,userToken:{},appCode:{},oldPwd:{},newPwd:{}",userToken,appCode,oldPwd,newPwd);
          try{
            UserInfo userSession = UserRedisSessionUtils.getUserSession(userToken);
           
            //修改数据库密码记录
            UserInfo  user= new UserInfo();
            user.setUserId(userSession.getUserId());
            localUserService.modifyPwd(user, newPwd);
            //删除本地redis登陆信息
            UserRedisSessionUtils.delUserSession(userToken); 
            UserRedisSessionUtils.delAccessTokenByAppCode(appCode, userToken, TerminalType.ANDROID);
            UserRedisSessionUtils.delAccessTokenByAppCode(appCode, userToken, TerminalType.ISO);
            UserRedisSessionUtils.delAccessTokenByAppCode(appCode, userToken, TerminalType.PC);
            //删除xAuthToken
           
            UserRedisSessionUtils.delXAuthToken(userSession.getUserId());
          }
          catch(Exception e){
              LOGGER.error("修改密码：清除本地登陆信息失败",e);
          }
          }
        return resultVo;

    }

    @Override
    public BaseResultVo<ExtendUserInfo> getUserInfo(String userToken)
            throws Exception {
        LOGGER.info("传入参数userToken:{}",userToken);
        BaseResultVo<ExtendUserInfo> resultVo =  new BaseResultVo<ExtendUserInfo>();
        if (StringUtils.isEmpty(userToken)) {
            resultVo.setResponseCode(UserCenterErrorCode.USER_TOKEN_EMPTY);
            resultVo.setErrorMsg("获取用户信息失败：用户token为空");
            return resultVo;
        }
        com.rongcapital.usercenter.api.po.ExtendUserInfo extendUserInfo = new com.rongcapital.usercenter.api.po.ExtendUserInfo();
        UserInfo userSession = UserRedisSessionUtils.getUserSession(userToken);
        if (userSession == null) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("获取用户信息失败:redis操作异常");
            return resultVo;
        }
        if (userSession != null) {
            extendUserInfo.setUserName(userSession.getUserId());// 登录名
            extendUserInfo.setUserRealName(userSession.getUserType() == 2?userSession.getPersonInfo().getUserCname():userSession.getCompanyInfo().getCompanyName());// 中文名
            extendUserInfo.setMobilePhone(userSession.getPersonInfo().getMobilePhone());// 手机号
            extendUserInfo.setEmail(userSession.getUserType() == 2 ?userSession.getPersonInfo().getEmail():userSession.getCompanyInfo().getCorporateMail());// 邮箱
            extendUserInfo.setUserType(userSession.getUserType() == 2 ? UserInfoPo.USERTYPE.PERSON
                    : UserInfoPo.USERTYPE.COMPANY);// 用户类型
            resultVo.setSuccess(true);
            resultVo.setResultData(extendUserInfo);
        }

        return resultVo;
    }

    @Override
    public BaseResultVo<Boolean> checkAuth(String userToken, String appCode, TerminalType terminalType) {
        LOGGER.info("传入参数userToken:{},appCode:{},terminalType:{}" , userToken , appCode , terminalType);
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        boolean isLogin=false;
        if (StringUtils.isEmpty(userToken)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_TOKEN_EMPTY);
            resultVo.setErrorMsg("校验权限失败：用户token为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(appCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("应用appcode不能为空");
            return resultVo;
        }
        
        String fileConfig="";
        try{
            fileConfig= this.getConfigfile(appCode);
        }
        catch(NotFoundException e){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_APPCODE);
            resultVo.setErrorMsg("应用appcode不存在");
            return resultVo;
        }
        // 第一步查询 redis 是否有值

        // 查询是否是本应用登陆
        RedisAuthInfo authInfoSelf =
                UserRedisSessionUtils.getAuthInfoWithTerminalType(appCode, userToken, terminalType);
        if (null != authInfoSelf) {
            BaseResultVo<Boolean> userAuth =
                    this.caasUserServiceProxy.checkAuth(fileConfig,appCode, authInfoSelf.getAccessToken(), "" ,"");
            if (userAuth.isSuccess()) {// 校验通过
                resultVo.setSuccess(true);
                resultVo.setResultData(true);
                Date lastLoginTime = null;
                //拿最后登录时间
                try {
                    UserInfo userSession = UserRedisSessionUtils.getUserSession(userToken);
                    lastLoginTime=userSession.getLastLoginTime();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    LOGGER.error("",e);
                }
                if (userAuth.getResultData() || compareTime(lastLoginTime, compareTimePeriod)) {// accessToken即将过期 获取比较时间大于5分钟
                    try {
                      
                        BaseResultVo<AuthResultVo> refreshToken =
                                this.caasUserServiceProxy.refreshToken(fileConfig,appCode, authInfoSelf.getRefreshToken());
                        UserRedisSessionUtils.getUserSession(userToken);
                        UserRedisSessionUtils.getUserSession(userToken).getMaxExpireTime();
                        AuthResultVo resultData = refreshToken.getResultData();
                       //CAAS生成的失效时间大于当前redis失效时间,此时需要更新本地redis对应失效时间
                        if (refreshToken.isSuccess()) {// 更新redis数据
                            
                            UserRedisSessionUtils.doRefleshUserInfo(appCode, userToken, terminalType,
                                    resultData);
                        }
                    } catch (Exception e) {
                        LOGGER.error("更新用户accessToken失败", e);// 只记日志
                    }
                    //
                }
                // 如果不是即将过期 ，拿当前时间-上次登录时间是否大于5分钟 ，大于5分钟直接更新
                // 更新redis，更新caas 调用refresh方法
            } else {
                // 校验不通过,删除redis存在的"假数据" 直接返回校验失败
                try {
                    // 删除具体应用accessToken
                    UserRedisSessionUtils.delAccessTokenByAppCode(appCode, userToken, terminalType);
                } catch (Exception e) {
                    LOGGER.error("删除redis校验不通过对 accessToken");
                }
                if(userAuth!=null){
                   resultVo.setErrorMsg(userAuth.getErrorMsg());
                   resultVo.setResponseCode(userAuth.getResponseCode());
                }
                resultVo.setSuccess(false);
                return resultVo;
            }

        }
        if (null == authInfoSelf) {

            Map<String,RedisAuthInfo> redisAuthInfoMap;
            try {
                redisAuthInfoMap = UserRedisSessionUtils.getRedisAuthInfoList(userToken, terminalType);
            } catch (Exception e) {
                LOGGER.error("校验失败,redis error", e);
                resultVo.setResponseCode(UserCenterErrorCode.USER_TOKEN_EMPTY);
                resultVo.setErrorMsg("校验权限失败：redis操作失败");
                return resultVo;
            }
            // redis无值 返回校验失败，未登录
            if (redisAuthInfoMap == null||redisAuthInfoMap.isEmpty()) {//
                resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOLOGIN_ERROR);
                resultVo.setErrorMsg("校验权限失败：用户未登陆");
                return resultVo;
            }

            if (redisAuthInfoMap != null ) {
                // 当前 redis accesstoken + a系统
                // 非本应用登陆 判断是否是本CAAS其他系统跳转
                    Set<String> keys = redisAuthInfoMap.keySet();
                    RedisAuthInfo redisAuthInfoStr= new RedisAuthInfo();
                    String  singleAppCode;
                    if(keys != null) {
                    Iterator<String> iterator = keys.iterator( );
                    while(iterator.hasNext( )) {
                        singleAppCode = iterator.next().toString();//为遍历对应appCode
                        redisAuthInfoStr =(RedisAuthInfo) redisAuthInfoMap.get(singleAppCode);
                        BaseResultVo<String> resultVO =
                                this.caasUserServiceProxy.getAuthCode(this.getConfigfile(singleAppCode),singleAppCode, redisAuthInfoStr.getAccessToken());
                        if (!resultVO.isSuccess()) {// 删除有校验不通过的redis记录
                            // 删除具体应用accessToken
                            UserRedisSessionUtils.delAccessTokenByAppCode(singleAppCode, userToken, terminalType);
                        }
                        if (resultVO.isSuccess() && !StringUtils.isEmpty(resultVO.getResultData())) {// 有校验成功的 表示从其他系统跳转
                            BaseResultVo<AuthResultVo> userAuthResultVO =
                                    this.caasUserServiceProxy
                                            .userAuth(this.getConfigfile(appCode), appCode, resultVO.getResultData());
                            isLogin=true; //表示登陆成功
                            if (userAuthResultVO.isSuccess()) {// 重授权成功
                                try {
                                    // 更新redis值
                                    //String newAccessToken = userAuthResultVO.getResultData().getAccessToken();
                                    UserRedisSessionUtils.doUserLoginInfo(appCode + "", terminalType, userToken,
                                            UserRedisSessionUtils.getUserSession(userToken),
                                            userAuthResultVO.getResultData());
                                } catch (Exception e) {
                                    LOGGER.warn("登录插入redis数据发生异常", e);
                                }
                            }

                        }
                    }
                }
              resultVo.setSuccess(isLogin);
              resultVo.setResultData(isLogin);
            }
        }
        
            
            return resultVo;
      
    }

    @Override
    public BaseResultVo<Boolean> logout(String userToken, String appCode, TerminalType terminalType) {
        LOGGER.info("传入参数userToken:{},userToken:{},appCode:{},terminalType:{}" ,userToken , appCode , terminalType);
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        if (StringUtils.isEmpty(userToken)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_TOKEN_EMPTY);
            resultVo.setErrorMsg("用户注销失败：用户token为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(appCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("应用appcode不能为空");
            return resultVo;
        }
        
        if (!terminalType.equals(TerminalType.ANDROID) && !terminalType.equals(TerminalType.ISO)
                && !terminalType.equals(TerminalType.PC)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_TERMINALTYPE_ERROR);
            resultVo.setErrorMsg("用户注销失败：终端类型错误或为空");
            return resultVo;
        }
        try{
            this.getConfigfile(appCode);
        }
        catch(NotFoundException e){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_APPCODE);
            resultVo.setErrorMsg("应用appcode不存在");
            return resultVo;
        }
        try {
            Map<String,RedisAuthInfo> redisAuthInfoMap;
            redisAuthInfoMap = UserRedisSessionUtils.getRedisAuthInfoList(userToken, terminalType);
            if (redisAuthInfoMap == null||redisAuthInfoMap.isEmpty()) {
                resultVo.setSuccess(false);
                resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
                resultVo.setErrorMsg("用户注销失败：redis存储数据为空");
                return resultVo;
            }
            if (redisAuthInfoMap != null ) {
                if (TerminalType.PC == terminalType) {// 若传入终端为pc,则删除所有的对应 CAAS应用
                        Set<String> keys = redisAuthInfoMap.keySet();
                        RedisAuthInfo redisAuthInfoStr= new RedisAuthInfo();
                        if(keys != null) {
                        Iterator<String> iterator = keys.iterator( );
                        while(iterator.hasNext( )) {
                            String  key = iterator.next().toString();//为遍历对应appCode
                            redisAuthInfoStr =(RedisAuthInfo) redisAuthInfoMap.get(key);
                            BaseResultVo<Boolean> logoutPc = caasUserServiceProxy.logout(this.getConfigfile(key),key, redisAuthInfoStr.getAccessToken());
                            if (logoutPc.isSuccess()) {// CAAS注销成功后，删除本地redis信息
                                UserInfo userSession = UserRedisSessionUtils.getUserSession(userToken);
                                
                                UserLoginHistory userLoginHistory = new UserLoginHistory();
                                try{
                                    userLoginHistory.setLoginType(2);
                                    userLoginHistory.setLongSource(Integer.valueOf(terminalType.toString()));
                                    if(null != userSession){
                                        
                                        userLoginHistory.setUserInfoId(userSession.getUserInfoId());
                                    }
                                    userLoginHistoryMapper.insertSelective(userLoginHistory );
                                }catch(Exception e){
                                    LOGGER.warn("插入用户登录历史信息发生异常{}",userLoginHistory,e);
                                }
                                
                                UserRedisSessionUtils.doLogout(key, userToken, terminalType);//删除 accessToken 对应redis记录
                                UserRedisSessionUtils.delUserSession(userToken); //删除 该userToken对应 session记录
                                
                                //删除xAuthToken
                               
                                UserRedisSessionUtils.delXAuthToken(userSession.getUserId());
                            }
                        }
                       
                    }
                    
                }
                if (TerminalType.ISO == terminalType || TerminalType.ANDROID == terminalType) {// 若应用为app端，只退出当前应用
                    BaseResultVo<Boolean> logoutApp = caasUserServiceProxy.logout(this.getConfigfile(appCode), appCode,this.getAccessToken(appCode, userToken));
                    if(logoutApp.isSuccess()){
                        UserInfo userSession = UserRedisSessionUtils.getUserSession(userToken);   
                      
                        UserLoginHistory userLoginHistory = new UserLoginHistory();
                        try{
                            userLoginHistory.setLoginType(2);
                            userLoginHistory.setLongSource(Integer.valueOf(terminalType.toString()));
                            if(null != userSession){
                                
                                userLoginHistory.setUserInfoId(userSession.getUserInfoId());
                            }
                            userLoginHistoryMapper.insertSelective(userLoginHistory );
                        }catch(Exception e){
                            LOGGER.warn("插入用户登录历史信息发生异常{}",userLoginHistory,e);
                        }
                        
                        UserRedisSessionUtils.delAccessTokenByAppCode(appCode, userToken, terminalType);
                        Map<String, RedisAuthInfo> redisAuthInfoList = UserRedisSessionUtils.getRedisAuthInfoList(userToken, terminalType);
                    if(redisAuthInfoList.isEmpty()){//删除当前应用，若没有其他应用则直接删除 整个userToken
                        UserRedisSessionUtils.delUserSession(userToken);
                    }
                    //删除xAuthToken
                   
                    UserRedisSessionUtils.delXAuthToken(userSession.getUserId());
                    }else{
                        resultVo.setSuccess(false);
                        resultVo.setResponseCode(logoutApp.getResponseCode());
                        resultVo.setErrorMsg(logoutApp.getErrorMsg());
                        return resultVo;
                    }
                }

            }
        } catch (Exception e) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("用户注销失败");
            LOGGER.error("用户注销失败", e);
            return resultVo;
        }
        return resultVo;
    }

    // 授权
    public BaseResultVo<AuthResultVo> Auth(String authCode, String appCode) {
        LOGGER.info("传入参数authCode:{},appCode:{}",authCode,appCode);
        BaseResultVo<AuthResultVo> resultVo = new BaseResultVo<AuthResultVo>();
        if (StringUtils.isEmpty(authCode)) {
            resultVo.setResponseCode(UserCenterErrorCode.USER_TOKEN_EMPTY);
            resultVo.setErrorMsg("用户授权失败：用户authCode为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(appCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("应用appcode不能为空");
            return resultVo;
        }
        return caasUserServiceProxy.userAuth(this.getConfigfile(appCode), authCode, appCode);
    }

    // 获取授权码authCode
    public BaseResultVo<String> getAuthCode(String userToken, String appCode) {
        LOGGER.info("传入参数userToken:{},appCode:{}" , userToken ,appCode);
        BaseResultVo<String> resultVo = new BaseResultVo<String>();
        if (StringUtils.isEmpty(userToken)) {

            resultVo.setResponseCode(UserCenterErrorCode.USER_TOKEN_EMPTY);
            resultVo.setErrorMsg("获取用户授权码失败：用户token为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(appCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("应用appcode不能为空");
            return resultVo;
        }
        String accessToken = this.getAccessToken(appCode, userToken);
        return caasUserServiceProxy.getAuthCode(this.getConfigfile(appCode),appCode, accessToken);
    }

    // 通过userToken 获取accessToken
    public String getAccessToken(String appCode, String userToken) {
        String accessToken = "";
        RedisAuthInfo authInfo = UserRedisSessionUtils.getAuthInfo(appCode, userToken);

        if (authInfo != null) {
            accessToken = authInfo.getAccessToken();
        }
        return accessToken;
    }

    public boolean compareTime(Date dateOld, String time) {
        long currentTime = new Date().getTime();
        long oldTime = dateOld.getTime();
        int result = (int)(( currentTime- oldTime) / 1000);
        time=StringUtils.isEmpty(time)?"300":time; 
      //time=StringUtils.isEmpty(time)?"10":time; 
        return result>Integer.parseInt(time);
    }
    
    public String   getConfigfile(String appCode){
        return CaasConfigPathUtils.getConfigfile(appCode);
    }
}
