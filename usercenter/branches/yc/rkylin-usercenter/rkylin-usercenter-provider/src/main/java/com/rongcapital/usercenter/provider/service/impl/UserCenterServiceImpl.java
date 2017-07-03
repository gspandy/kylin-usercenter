package com.rongcapital.usercenter.provider.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.rkylin.usercenter.util.CaasUserServiceProxy;
import com.rkylin.wheatfield.model.CommonResponse;
import com.rongcapital.usercenter.api.po.CompanyInfo;
import com.rongcapital.usercenter.api.po.IdentifyInfoVo;
import com.rongcapital.usercenter.api.po.NologinReturnUserInfo;
import com.rongcapital.usercenter.api.po.UserInfoDetail;
import com.rongcapital.usercenter.api.po.UserInfoPo;
import com.rongcapital.usercenter.api.service.UserCenterService;
import com.rongcapital.usercenter.api.vo.AuthResultVo;
import com.rongcapital.usercenter.api.vo.BaseResultVo;
import com.rongcapital.usercenter.api.vo.BindAccountVo;
import com.rongcapital.usercenter.api.vo.LoginInfo.TerminalType;
import com.rongcapital.usercenter.api.vo.OpenAccountCompany;
import com.rongcapital.usercenter.api.vo.OpenAccountPerson;
import com.rongcapital.usercenter.api.vo.UserRegiesterInfo.RegiesterType;
import com.rongcapital.usercenter.provider.constant.ResponseConstants;
import com.rongcapital.usercenter.provider.constant.UserCenterErrorCode;
import com.rongcapital.usercenter.provider.dao.CompanyInfoMapper;
import com.rongcapital.usercenter.provider.dao.PersonInfoMapper;
import com.rongcapital.usercenter.provider.dao.UcOrgMappingMapper;
import com.rongcapital.usercenter.provider.dao.UserInfoMapper;
import com.rongcapital.usercenter.provider.dao.UserLoginHistoryMapper;
import com.rongcapital.usercenter.provider.dao.UserLoginMapper;
import com.rongcapital.usercenter.provider.po.PersonInfo;
import com.rongcapital.usercenter.provider.po.UcOrgMapping;
import com.rongcapital.usercenter.provider.po.UcOrgMappingExample;
import com.rongcapital.usercenter.provider.po.UserInfo;
import com.rongcapital.usercenter.provider.po.UserLogin;
import com.rongcapital.usercenter.provider.po.UserLoginHistory;
import com.rongcapital.usercenter.provider.po.UcOrgMappingExample.Criteria;
import com.rongcapital.usercenter.provider.service.LocalUserService;
import com.rongcapital.usercenter.provider.service.dubbo.AccountDubbo;
import com.rongcapital.usercenter.provider.util.CaasConfigPathUtils;
import com.rongcapital.usercenter.provider.util.EncryptionUtil;
import com.rongcapital.usercenter.provider.util.UserCenterConfig;
import com.rongcapital.usercenter.provider.util.UserRedisSessionUtils;
import com.rongcapital.usercenter.provider.vo.RedisAuthInfo;
import com.ruixue.serviceplatform.commons.exception.NotFoundException;

@Service
@Configuration  
public class UserCenterServiceImpl implements UserCenterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCenterServiceImpl.class);

    private static final String compareTimePeriod = UserCenterConfig.getProperty("compare.time.period");
  
    private static final String MECHANT_ACTUAL_ORGCODE1 = UserCenterConfig.getProperty("uc.actual.orgcode.mechant1");
    
    public static final String CAAS_CHECKAUTH_EXPIRETIME = UserCenterConfig.getProperty("uc.caas.checkauth.expireTime");

   
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
    
    @Autowired
    private UcOrgMappingMapper ucOrgMappingMapper;
    
    @Autowired
    private AccountDubbo accountDubbo;

    @Override
    public BaseResultVo<Boolean> isLoginNameExists(String loginName,String orgCode, String appCode) {
        LOGGER.info("校验用户名是否存在,入参：loginName{},orgCode{},appCode{}",loginName,orgCode,appCode);
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        if (StringUtils.isEmpty(loginName)||StringUtils.isEmpty(orgCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("判断当前机构下用户名是否存在失败：用户登录名或者机构号为空");
            return resultVo;
        }
        try{
           
            UserInfo userInfoSelect = this.userInfoMapper.selectByLoginName(loginName,orgCode);
            if(userInfoSelect==null){
                resultVo.setSuccess(true);
                resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_ERROR);
                resultVo.setResultData(false);
                resultVo.setErrorMsg("用户不存在");
                return resultVo;
            }else {
                resultVo.setSuccess(true);
                resultVo.setResultData(true);// 用户名已存在
            }
  
            
//            
//         BaseResultVo<String> loginNameExists = caasUserServiceProxy.isLoginNameExists(this.getConfigfile(appCode), appCode, loginName+orgCode, "");
//         if(loginNameExists.isSuccess()){
//             resultVo.setSuccess(true);
//             resultVo.setResultData(true);//用户名已存在
//         }else{
//             resultVo.setSuccess(true);
//             resultVo.setResultData(false);//用户名不存在
//         }
        }catch(Exception e){
            LOGGER.error("判定当前机构下用户名是否存在失败:",e);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("判定当前机构下用户名是否存在失败:"+e.getMessage());
            return resultVo;
            
        }
        return resultVo;
        
    }

    
    @Override
    public BaseResultVo<Boolean>  passwordReset(String loginName,String orgCode,String accessTicket,String newPwd, String appCode) {
        LOGGER.info("传入参数loginName:{},orgCode:{},accessTicket:{},newPwd:{},appCode:{}",loginName,orgCode, accessTicket,newPwd,appCode);
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        
        if (StringUtils.isEmpty(loginName)||StringUtils.isEmpty(orgCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("密码重置失败：用户登录名或者机构号为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(accessTicket)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.ACCESSTICKET_EMPTY);
            resultVo.setErrorMsg("密码重置失败：用户授权码为空");
            return resultVo;
        }

        if (StringUtils.isEmpty(appCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.APPCODE_EMPTY);
            resultVo.setErrorMsg("密码重置失败：应用appcode不能为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(newPwd)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_NEWPASSWORD_EMPTY);
            resultVo.setErrorMsg("密码重置失败：新密码传入为空");
            return resultVo;
        }
        try{
            this.getConfigfile(appCode);
        }
        catch(NotFoundException e){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_APPCODE);
            resultVo.setErrorMsg("密码重置失败：应用appcode不存在");
            return resultVo;
        }
        
        try{
           String  accessTicketExsit=UserRedisSessionUtils.getAccessTicket(loginName, orgCode);    
           if(!accessTicket.equals(accessTicketExsit)){
               resultVo.setSuccess(false);
               resultVo.setResponseCode(UserCenterErrorCode.ACCESSTICKET_NOTMATCH_ERROR);
               resultVo.setErrorMsg("密码重置失败：用户传递授权码和系统颁发不一致");
               return resultVo; 
               
           }    
            
        UserInfo userInfoSelect = this.userInfoMapper.selectByLoginName(loginName,orgCode);
       
        if(userInfoSelect==null){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_ERROR);
            resultVo.setErrorMsg("用户不存在");
            return resultVo;
        }
        UserLogin selectByPrimaryKey = userLoginMapper.selectByPrimaryKey(userInfoSelect.getUserInfoId());
        newPwd = EncryptionUtil.sha256Encry(newPwd, selectByPrimaryKey.getPwdSalt());
        
        // 重置
        resultVo = caasUserServiceProxy.resetPwd(this.getConfigfile(appCode), appCode, loginName+orgCode, newPwd);
        if(resultVo.isSuccess()){
            LOGGER.info("密码重置成功,userName:{},appCode:{},newPwd:{}",loginName+orgCode,appCode,newPwd);
            UserInfo  user= new UserInfo();
            user.setUserInfoId(userInfoSelect.getUserInfoId());
           try{
            UserRedisSessionUtils.delLoginKey(loginName, orgCode);   
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
        UserInfo userSession;
        try {
            userSession = UserRedisSessionUtils.getUserSession(userToken);
        } catch (Exception e1) {
            LOGGER.error("修改密码失败:redis操作失败",e1);
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("修改密码失败:用户token失效");
            return resultVo;
        }
        if (userSession == null) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("修改密码失败:用户token失效");
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
        
        try {
            UserLogin selectByPrimaryKey = userLoginMapper.selectByPrimaryKey(userSession.getUserInfoId());
            newPwd = EncryptionUtil.sha256Encry(newPwd, selectByPrimaryKey.getPwdSalt());
            oldPwd = EncryptionUtil.sha256Encry(oldPwd, selectByPrimaryKey.getPwdSalt());
        } catch (Exception e) {
            LOGGER.error("修改密码失败:",e);
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("修改密码失败:数据库操作异常");
            return resultVo;
        }
       
        resultVo= caasUserServiceProxy.updatePwd(fileConfig, appCode, accessToken, oldPwd, newPwd, vcode, xAuthToken);
        if(resultVo.isSuccess()){//修改密码成功，清除登陆信息，重新登陆
            LOGGER.info("密码修改成功,userToken:{},appCode:{},oldPwd:{},newPwd:{}",userToken,appCode,oldPwd,newPwd);
          try{
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
           
            UserRedisSessionUtils.delXAuthToken(userSession.getLoginName(),userSession.getOrgCode());
            //删除登陆失败次数信息
            UserRedisSessionUtils.delLoginKey(userSession.getLoginName(), userSession.getOrgCode());
          }
          catch(Exception e){
              LOGGER.error("修改密码：清除本地登陆信息失败",e);
          }
          }
        return resultVo;

    }

    @Override
    public BaseResultVo<UserInfoDetail> getUserInfo(String userToken)
            throws Exception {
        LOGGER.info("传入参数userToken:{}",userToken);
        BaseResultVo<UserInfoDetail> resultVo =  new BaseResultVo<UserInfoDetail>();
        if (StringUtils.isEmpty(userToken)) {
            resultVo.setResponseCode(UserCenterErrorCode.USER_TOKEN_EMPTY);
            resultVo.setErrorMsg("获取用户信息失败：用户token为空");
            return resultVo;
        }
        UserInfoDetail extendUserInfo = new UserInfoDetail();
        com.rongcapital.usercenter.api.po.PersonInfo personInfo = new  com.rongcapital.usercenter.api.po.PersonInfo();
        CompanyInfo companyInfo =new CompanyInfo();
        try {
            UserInfo userSession = UserRedisSessionUtils.getUserSession(userToken);
            if (userSession == null) {
                resultVo.setSuccess(false);
                resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
                resultVo.setErrorMsg("获取用户信息失败:用户token失效");
                return resultVo;
            }
            if (userSession != null) {
                extendUserInfo.setUserId(userSession.getUserId());// 
                
                
                extendUserInfo.setUserCode(userSession.getUserCode());
                
                if(MECHANT_ACTUAL_ORGCODE1.equals(userSession.getOrgCode())){//对于商户平台，注册时机构实际为虚拟机构，需从映射表重新获取
                    UcOrgMappingExample example = new UcOrgMappingExample();
                    Criteria createCriteria = example.createCriteria();
                    createCriteria.andOrgCodeVirtualEqualTo(userSession.getOrgCode());
                    createCriteria.andLoginNameEqualTo(userSession.getLoginName());
                    List<UcOrgMapping> selectByExample = ucOrgMappingMapper.selectByExample(example);
                    if(selectByExample!=null&&selectByExample.size()>0){
                        extendUserInfo.setUserCode(selectByExample.get(0).getOrgCodeActual()); 
                        userSession.setOrgCode(selectByExample.get(0).getOrgCodeActual());
                      //  UserRedisSessionUtils.updateUserSession(userToken, userSession);//更新缓存，下次查询不用查库
                    }
                }
                extendUserInfo.setIsIdentify(userSession.getIsIdentify());
                extendUserInfo.setLoginName(userSession.getLoginName());
                extendUserInfo.setOrgCode(userSession.getOrgCode());
                extendUserInfo.setStatus(userSession.getStatus());
                extendUserInfo.setUserType(userSession.getUserType());
                extendUserInfo.setVersion(userSession.getVersion());
                extendUserInfo.setLastLoginTime(userSession.getLastLoginTime());
                try {
                    if(RegiesterType.PERSON.value().equals(userSession.getUserType())){
                    BeanUtils.copyProperties(personInfo, userSession.getPersonInfo());
                    }
                    if(RegiesterType.COMPANY.value().equals(userSession.getUserType())){
                    BeanUtils.copyProperties(companyInfo, userSession.getCompanyInfo());
                    }
                } catch (Exception e) {
                    LOGGER.error("复制个人信息异常",e);
                    resultVo.setErrorMsg("获取用户信息接口异常："+ e.getMessage());
                    return resultVo;
                }
                extendUserInfo.setPersonInfo(personInfo);
                extendUserInfo.setCompanyInfo(companyInfo);
                resultVo.setSuccess(true);
                resultVo.setResultData(extendUserInfo);
            }
        } catch (Exception e) {
            LOGGER.error("获取用户信息失败，程序执行异常",e);
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("获取用户信息失败:程序执行异常");
            return resultVo;
        }

        return resultVo;
    }

    @Override
    public BaseResultVo<Boolean> checkAuth(String userToken,String userId,String orgCode,String appCode, boolean isreflashToken,TerminalType terminalType) {
        LOGGER.info("传入参数userToken:{},userId:{},orgCode:{},appCode:{},isreflashToken:{},terminalType:{}" , userToken ,userId,orgCode,appCode ,isreflashToken, terminalType);
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        boolean isLogin=false;
        if (StringUtils.isEmpty(userToken)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_TOKEN_EMPTY);
            resultVo.setErrorMsg("校验权限失败：用户token为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(userId)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("应用userId不能为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(orgCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("应用orgCode不能为空");
            return resultVo;
        }
        if (StringUtils.isEmpty(appCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("应用appcode不能为空");
            return resultVo;
        }
        
        
        
        
        String fileConfig="";
        RedisAuthInfo authInfoSelf =new   RedisAuthInfo();
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
        try{
         UserInfo userinfoFromRedis = UserRedisSessionUtils.getUserSession(userToken);
         if(null==userinfoFromRedis){
                resultVo.setSuccess(false);
                resultVo.setResponseCode(UserCenterErrorCode.USER_TOKEN_EMPTY);
                resultVo.setErrorMsg("登录失效，请重新登陆");
                return resultVo;
         }   
         authInfoSelf =
                UserRedisSessionUtils.getAuthInfoWithTerminalType(appCode, userToken, terminalType);
         
         
         LOGGER.info("------------------orgCode:{}",orgCode);
         LOGGER.info("------------------redisOrgCode:{}",userinfoFromRedis.getOrgCode(),orgCode.equals(userinfoFromRedis.getOrgCode()));
        if(!orgCode.equals(userinfoFromRedis.getOrgCode())||!userId.equals(userinfoFromRedis.getUserId())){
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_APPCODE);
            resultVo.setErrorMsg("当前用户token信息与参数userId,orgCode信息不匹配");
            return resultVo;
        }
        }catch(Exception e){
            LOGGER.error("",e);
        }
        
        try {
            if(UserRedisSessionUtils.isCaasCheckResult(userToken)&&null!=authInfoSelf){//登陆时保存记录校验 5分钟 ,每5分钟刷一次
                resultVo.setSuccess(true);
                resultVo.setResultData(true);
                return resultVo; 
            }
        } catch (Exception e) {
            LOGGER.error("取redis caas校验失败",e);
            
        }
        if (null != authInfoSelf) {
            BaseResultVo<Boolean> userAuth =
                    this.caasUserServiceProxy.checkAuth(fileConfig,appCode, authInfoSelf.getAccessToken(), "" ,"");
            if (userAuth.isSuccess()) {// 校验通过
                resultVo.setSuccess(true);
                resultVo.setResultData(true);
                Date lastLoginTime = null;
                
                //缓存caas check结果 
                try {
                    UserRedisSessionUtils.setCaasCheckResult(userToken,Integer.parseInt(CAAS_CHECKAUTH_EXPIRETIME));
                } 
                catch (Exception e) {
                    LOGGER.error("缓存caas check结果失败",e);
                }
                
                
                
                if(isreflashToken){//如果为true  更新缓存时间
                //拿最后登录时间
                try {
                    UserInfo userSession = UserRedisSessionUtils.getUserSession(userToken);
                    lastLoginTime=userSession.getLastLoginTime();
                } catch (Exception e) {
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
                }
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
                                            .userAuth(this.getConfigfile(appCode),appCode, resultVO.getResultData());
                            if(userAuthResultVO.isSuccess()){//授权成功
                            isLogin=true; //表示登陆成功
                            }
                            if (userAuthResultVO.isSuccess()) {// 重授权成功
                                try {
                                    // 更新redis值
                                    //String newAccessToken = userAuthResultVO.getResultData().getAccessToken();
                                    UserRedisSessionUtils.doUserLoginInfo(appCode + "", terminalType, userToken,
                                            UserRedisSessionUtils.getUserSession(userToken),
                                            userAuthResultVO.getResultData());
                                    
                                    
                                    //缓存caas check结果 
                                   UserRedisSessionUtils.setCaasCheckResult(userToken,Integer.parseInt(CAAS_CHECKAUTH_EXPIRETIME));
                                    
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
                                resultVo.setSuccess(true);
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
                               
                                UserRedisSessionUtils.delXAuthToken(userSession.getUserId(),userSession.getOrgCode());
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
                        resultVo.setSuccess(true);
                        UserRedisSessionUtils.delAccessTokenByAppCode(appCode, userToken, terminalType);
                        Map<String, RedisAuthInfo> redisAuthInfoList = UserRedisSessionUtils.getRedisAuthInfoList(userToken, terminalType);
                    if(redisAuthInfoList.isEmpty()){//删除当前应用，若没有其他应用则直接删除 整个userToken
                        UserRedisSessionUtils.delUserSession(userToken);
                    }
                    //删除xAuthToken
                   
                    UserRedisSessionUtils.delXAuthToken(userSession.getUserId(),userSession.getOrgCode());
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
       try{
        RedisAuthInfo authInfo = UserRedisSessionUtils.getAuthInfo(appCode, userToken);

        if (authInfo != null) {
            accessToken = authInfo.getAccessToken();
        }}catch(Exception e){
            LOGGER.info("通过usertoken获取 accesstoken失败",e);
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


    @Override
    public BaseResultVo<String> identify(IdentifyInfoVo identifyInfoVo) {

        BaseResultVo<String> resultVo = new BaseResultVo<String>();
        if(null==identifyInfoVo){
          resultVo.setResponseCode(UserCenterErrorCode.USER_ENTITY_EMPTY); 
          resultVo.setErrorMsg("实名制失败:实名制对象实体为空");
          return resultVo;
        }
        LOGGER.info("实名制接口入参:identifyInfoVo{}",identifyInfoVo.toString());
        StopWatch sw = new StopWatch("实名制接口identify");
       
        if(StringUtils.isEmpty(identifyInfoVo.getUserToken())){
            resultVo.setResponseCode(UserCenterErrorCode.USER_TOKEN_EMPTY); 
            resultVo.setErrorMsg("实名制失败:用户token为空");
            return resultVo; 
        }
        if(StringUtils.isEmpty(identifyInfoVo.getIdcardNo())){
            resultVo.setResponseCode(UserCenterErrorCode.IDCARDNO_EMPTY); 
            resultVo.setErrorMsg("实名制失败:证件号为空");
            return resultVo;
        }
        if(StringUtils.isEmpty(identifyInfoVo.getRealName())){
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY); 
            resultVo.setErrorMsg("实名制失败:真实姓名为空");
            return resultVo;
        }
        try {
            sw.start("通过token查询缓存用户信息");
            UserInfo userSession = UserRedisSessionUtils.getUserSession(identifyInfoVo.getUserToken());
            if(null==userSession){
                resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY); 
                resultVo.setErrorMsg("实名制失败:用户token不存在或者失效");
                return resultVo;
            }
            UserInfo identifyUser= new UserInfo();
            PersonInfo personInfo = new PersonInfo(); 
            String realName;//真实姓名
            String idcardNo=identifyInfoVo.getIdcardNo();//身份证号/营业执照号
            String idCardNoAddress; //身份证地址
            String national; //国籍
            String ethnic ;  //民族
            String policeStation;//所属派出所
            String idcardNoExpireTime;//证件号过期时间
            
            if(!StringUtils.isEmpty(identifyInfoVo.getEthnic())){//民族
                ethnic=identifyInfoVo.getEthnic();
                personInfo.setEthnic(ethnic);
            }
           
            if(!StringUtils.isEmpty(identifyInfoVo.getIdCardNoAddress())){//身份证地址
                idCardNoAddress=identifyInfoVo.getIdCardNoAddress();
                personInfo.setIdNumberAddress(idCardNoAddress);
            }
            if(!StringUtils.isEmpty(identifyInfoVo.getIdcardNoExpireTime())){//身份证过期时间
                idcardNoExpireTime=identifyInfoVo.getIdcardNoExpireTime();
                personInfo.setIdNumberExpireTime(idcardNoExpireTime);
            }
            if(!StringUtils.isEmpty(identifyInfoVo.getNational())){//国籍
                national=identifyInfoVo.getNational();
                personInfo.setNational(national);
            }
            if(!StringUtils.isEmpty(identifyInfoVo.getPoliceStation())){//派出所
                policeStation=identifyInfoVo.getPoliceStation();
                personInfo.setPoliceStation(policeStation);
            }
            if(!StringUtils.isEmpty(identifyInfoVo.getRealName())){//真是姓名
                realName=identifyInfoVo.getRealName();
                personInfo.setUserCname(realName);
            }
            String userCode=userSession.getUserCode();
            personInfo.setUserInfoId(userSession.getUserInfoId());
            sw.stop();
            sw.start("通过身份证号查询表实名用户信息");
            List<UserInfo> selectByIdNumber = userInfoMapper.selectByIdNumber(identifyInfoVo.getIdcardNo());
            if(selectByIdNumber!=null&&selectByIdNumber.size()>0){
                for(UserInfo userInfo:selectByIdNumber){
                    if(1==userInfo.getIsIdentify()){
                        identifyUser=userInfo;
                        userCode=userInfo.getUserCode();
                        break;
                    }
                }
            }
                if(userSession.getOrgCode().equals(identifyUser.getOrgCode())){//数据查询的身份证所需机构跟当前机构相同
                    resultVo.setResponseCode(UserCenterErrorCode.IDCARD_HAS_IDENTIFY);
                    resultVo.setErrorMsg("用户实名制失败:该机构下用户已实名制");
                    return resultVo;
                }
           
            userSession.setIdCardNumber(idcardNo);
            userSession.setIsIdentify(1);
            userSession.setUpdatedTime(null);
            userSession.setUserCode(userCode);
            
            personInfo.setUpdatedTime(null);
            personInfo.setIdNumber(idcardNo);
            userSession.setPersonInfo(personInfo);
            sw.stop();
            sw.start("更新实名信息和个人信息表");
            localUserService.modifyIsidentify(userSession);
            localUserService.modifyPersonInfo(personInfo);
            sw.stop();
            sw.start("更新实名redis缓存信息");
            UserRedisSessionUtils.updateUserSession(identifyInfoVo.getUserToken(), userSession);
            resultVo.setSuccess(true);
            resultVo.setResultData(userCode);
            sw.stop();
        } catch (Exception e) {
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("用户实名制失败:程序执行异常"+e.getMessage());
            LOGGER.error("用户实名制失败:程序执行异常",e);
        }
        LOGGER.info(sw.prettyPrint());
        return resultVo;
    }


    @Override
    public BaseResultVo<Integer> getErrorLoginTime(String userLoginName, String orgCode) {
        BaseResultVo<Integer>  vo = new BaseResultVo<Integer>();
        LOGGER.info("获取用户登录失败次数接口入参：userLoginName{},orgCode{}",userLoginName,orgCode);
        try{
           if(!UserRedisSessionUtils.isExsitErrorLoginTime(userLoginName, orgCode)){
               vo.setSuccess(true);
               vo.setResultData(0);
               return vo;
           } 
           String time = UserRedisSessionUtils.getErrorLoginTime(userLoginName, orgCode);
           vo.setSuccess(true);
           vo.setResultData(Integer.parseInt(time));
        }
        catch(Exception e){
            LOGGER.error("获取用户登录失败次数失败",e);
            vo.setErrorMsg("获取用户登录失败次数失败"+e);
        }
        return vo;
    }


    @Override
    public BaseResultVo<Boolean> isIdentify(String loginName, String orgCode) {
        LOGGER.info("获取用户实名制状态接口入参：loginName{},orgCode{}",loginName,orgCode);
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        if (StringUtils.isEmpty(loginName)||StringUtils.isEmpty(orgCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("判断用户是否实名制失败：用户登录名或者机构号为空");
            return resultVo;
        }
        try{
        UserInfo userInfoByLoginName = localUserService.getUserInfoByLoginName(loginName,orgCode); 
        if(null==userInfoByLoginName){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_ERROR);
            resultVo.setErrorMsg("用户不存在");
            return resultVo;
        }
        
        
        if(userInfoByLoginName!=null){
            if(userInfoByLoginName.getIsIdentify()>0){
                resultVo.setSuccess(true);
                resultVo.setResultData(true);//已实名制
            }
            else{
               resultVo.setSuccess(true);
               resultVo.setResultData(false);//未实名制
            }
        }
        }
        catch(Exception e){
            LOGGER.error("查询数据库失败",e);
            resultVo.setErrorMsg("获取信息失败");
            return resultVo;
        }
        return resultVo;
    }


    @Override
    public BaseResultVo<Boolean> isMatchLoginNameAndIdNumber(String loginName, String orgCode, String idNumber) {
        LOGGER.info("用户与证件号是否匹配接口入参：loginName{},orgCode{},idNumber{}",loginName,orgCode,idNumber);
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        if (StringUtils.isEmpty(loginName)||StringUtils.isEmpty(orgCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("判断登录名与证件号是否匹配失败：用户登录名或者机构号为空");
            return resultVo;
        }
        if(StringUtils.isEmpty(idNumber)){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("判断登录名与证件号是否匹配失败：证件号码为空");
            return resultVo;
        }
        try{
        UserInfo userInfoByLoginName = localUserService.getUserInfoByLoginName(loginName,orgCode); 
        if(null==userInfoByLoginName){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_ERROR);
            resultVo.setErrorMsg("用户不存在");
        }
        if(userInfoByLoginName!=null){
            if(idNumber.equalsIgnoreCase(userInfoByLoginName.getIdCardNumber())){
                resultVo.setSuccess(true);
                resultVo.setResultData(true);//用户名和证件号匹配
            }
            else{
                resultVo.setSuccess(true);
                resultVo.setResultData(false);//用户名和证件号不匹配
            }
        }
    }  catch(Exception e){
        LOGGER.error("查询数据库失败",e);
        resultVo.setErrorMsg("匹配用户和证件号信息失败");
        return resultVo;
    }
    return resultVo;
    }


    @Override
    public BaseResultVo<NologinReturnUserInfo> getNologinUserInfo(String userLoginName, String orgCode) {
        BaseResultVo<NologinReturnUserInfo> resultVo = new BaseResultVo<NologinReturnUserInfo>();
        LOGGER.info("未登录状态获取用户信息接口入参：userLoginName{},orgCode{}",userLoginName,orgCode);
        if (StringUtils.isEmpty(userLoginName)||StringUtils.isEmpty(orgCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("获取为登陆用户信息失败 ：用户登录名或者机构号为空");
            return resultVo;
        }
        try{
        UserInfo userInfo = userInfoMapper.selectByLoginName(userLoginName, orgCode);
        NologinReturnUserInfo nologinReturnUserInfo = new NologinReturnUserInfo();
        if(null!=userInfo){
            PersonInfo personInfo = userInfo.getPersonInfo();
            if (StringUtils.isEmpty(personInfo.getMobilePhone())) {
                nologinReturnUserInfo.setMobilePhone(personInfo.getMobilePhone());
            }
            if (StringUtils.isEmpty(personInfo.getFixedPhone())) {
                nologinReturnUserInfo.setMobilePhone(personInfo.getFixedPhone());
            }
            if (StringUtils.isEmpty(personInfo.getEmail())) {
                nologinReturnUserInfo.setMobilePhone(personInfo.getEmail());
            }
            
                resultVo.setSuccess(true);
                resultVo.setResultData(nologinReturnUserInfo);
            
        }
        else{
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_EXSIT_USERNAME);
            resultVo.setErrorMsg("查询用户信息为空,用户不存在");
            
        }
        }catch(Exception e){
            LOGGER.error("未登录状态,通过登陆名机构号查询用户信息失败",e);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("未登录状态,通过登陆名机构号查询用户信息失败:程序执行异常,"+e.getMessage());
            return resultVo;
        }
        return resultVo;
    }


    @Override
    public BaseResultVo<String> getAccessTicket(String userLoginName, String orgCode,int expireTime) {
        LOGGER.info("getAccessTicket接口入参：userLoginName{},orgCode{},expireTime",userLoginName,orgCode,expireTime);
        BaseResultVo<String> resultVo = new BaseResultVo<String>();
        if (StringUtils.isEmpty(userLoginName)||StringUtils.isEmpty(orgCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("获取授权码失败：用户登录名或者机构号为空");
            return resultVo;
        }
        try {
            String accessTicket=UUID.randomUUID().toString().replace("-", "");
            UserRedisSessionUtils.setAccessTicket(userLoginName, orgCode, accessTicket, expireTime);
            resultVo.setSuccess(true);
            resultVo.setResultData(accessTicket);
        } catch (Exception e) {
            LOGGER.error("获取授权码失败",e);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("获取授权码失败:程序执行异常,"+e.getMessage());
            return resultVo;
        }
        return resultVo;
    }


    @Override
    public BaseResultVo<Boolean> modifyIdentify(IdentifyInfoVo identifyInfoVo) {
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
       
        if(null==identifyInfoVo){
          resultVo.setResponseCode(UserCenterErrorCode.USER_ENTITY_EMPTY); 
          resultVo.setErrorMsg("修改实名制失败:实名制对象实体为空");
          return resultVo;
        }
        LOGGER.info("修改实名制接口入参identifyvo{}",identifyInfoVo.toString());
        if(StringUtils.isEmpty(identifyInfoVo.getIdcardNo())){
            resultVo.setResponseCode(UserCenterErrorCode.IDCARDNO_EMPTY); 
            resultVo.setErrorMsg("修改实名制失败:证件号为空");
            return resultVo;
        }
        if(StringUtils.isEmpty(identifyInfoVo.getRealName())){
            resultVo.setResponseCode(UserCenterErrorCode.IDCARDNO_EMPTY); 
            resultVo.setErrorMsg("修改实名制失败:真实姓名为空");
            return resultVo;
        }
        try {
            UserInfo userSession = UserRedisSessionUtils.getUserSession(identifyInfoVo.getUserToken());
            if(null==userSession){
                resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY); 
                resultVo.setErrorMsg("修改实名制失败:用户token不存在或者失效");
                return resultVo;
            }
            UserInfo identifyUser= new UserInfo();
            PersonInfo personInfo = new PersonInfo(); 
            String realName;//真实姓名
            String idcardNo=identifyInfoVo.getIdcardNo();//身份证号/营业执照号
            String idCardNoAddress; //身份证地址
            String national; //国籍
            String ethnic ;  //民族
            String policeStation;//所属派出所
            String idcardNoExpireTime;//证件号过期时间
            
            if(!StringUtils.isEmpty(identifyInfoVo.getEthnic())){//民族
                ethnic=identifyInfoVo.getEthnic();
                personInfo.setEthnic(ethnic);
            }
           
            if(!StringUtils.isEmpty(identifyInfoVo.getIdCardNoAddress())){//
                idCardNoAddress=identifyInfoVo.getIdCardNoAddress();
                personInfo.setIdNumberAddress(idCardNoAddress);
            }
            if(!StringUtils.isEmpty(identifyInfoVo.getIdcardNoExpireTime())){//
                idcardNoExpireTime=identifyInfoVo.getIdcardNoExpireTime();
                personInfo.setIdNumberExpireTime(idcardNoExpireTime);
            }
            if(!StringUtils.isEmpty(identifyInfoVo.getNational())){//
                national=identifyInfoVo.getNational();
                personInfo.setNational(national);
            }
            if(!StringUtils.isEmpty(identifyInfoVo.getPoliceStation())){//
                policeStation=identifyInfoVo.getPoliceStation();
                personInfo.setPoliceStation(policeStation);
            }
            if(!StringUtils.isEmpty(identifyInfoVo.getRealName())){//
                realName=identifyInfoVo.getRealName();
                personInfo.setUserCname(realName);
            }
            long userInfId=userSession.getUserInfoId();
          //  Long personId=userSession.getPersonInfo().getId();
            String userCode=userSession.getUserCode();
            personInfo.setUserInfoId(userSession.getUserInfoId());
           // personInfo.setId(personId);
            List<UserInfo> selectByIdNumber = userInfoMapper.selectByIdNumber(identifyInfoVo.getIdcardNo());
            if(selectByIdNumber!=null&&selectByIdNumber.size()>0){
                for(UserInfo userInfo:selectByIdNumber){
                    if(1==userInfo.getIsIdentify()){
                        identifyUser=userInfo;
                    }
                }
            }
            if(null!=identifyUser.getUserInfoId()&&identifyUser.getUserInfoId()>0){
                if(userInfId!=identifyUser.getUserInfoId()){
                    resultVo.setResponseCode(UserCenterErrorCode.IDCARD_HAS_IDENTIFY);
                    resultVo.setErrorMsg("修改用户实名制修改失败:该证件号已在当前机构下实名制");
                    return resultVo;
                }
            }
          
            
            
            userSession.setIdCardNumber(idcardNo);
            userSession.setIsIdentify(1);
            userSession.setUpdatedTime(new Date());
            userSession.setUserCode(userCode);
          
            
            personInfo.setIdNumber(idcardNo);
            personInfo.setUpdatedTime(new Date());
            userSession.setPersonInfo(personInfo);
            
            
            localUserService.modifyIsidentify(userSession);
            localUserService.modifyPersonInfo(personInfo);
            UserRedisSessionUtils.updateUserSession(identifyInfoVo.getUserToken(), userSession);
            resultVo.setSuccess(true);
            
        } catch (Exception e) {
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("修改用户实名制失败:程序执行异常"+e.getMessage());
            LOGGER.error("用户实名制失败:程序执行异常",e);
            return resultVo;
        }
        return resultVo;
    }


    @Override
    public BaseResultVo<String> getUserCodeByUserId(String userId, String orgCode) {
        BaseResultVo<String> resultVo = new BaseResultVo<String>();
        if (StringUtils.isEmpty(userId)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("获取户口号失败：用户id为空");
            return resultVo;
        }
        try{
        UserInfo selectByUserId = userInfoMapper.selectByUserId(userId, orgCode);
        if(null==selectByUserId){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("获取户口号失败：当前机构下userId没有对应户口号");  
            return resultVo;
        }else{
            resultVo.setSuccess(true);
            resultVo.setResultData(selectByUserId.getUserCode());
        }
        }
        catch(Exception e){
            LOGGER.error("获取户口号失败,程序处理异常",e);
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            return resultVo;
        }
        return resultVo;
    }


    @Override
    public BaseResultVo<Boolean> isIdentifyByIdNumber(String idNumber,String orgCode) {
        LOGGER.info("通过身份证号获取用户实名制状态接口入参：idNumber{},orgCode{}",idNumber,orgCode);
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        if (StringUtils.isEmpty(idNumber)||StringUtils.isEmpty(orgCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("通过身份证号获取用户实名制失败：用户身份证号或者机构号为空");
            return resultVo;
        }
        try{
       // UserInfo userInfoByLoginName = localUserService.getUserInfoByLoginName(idNumber,orgCode); 
        
        List<UserInfo> selectByIdNumber = userInfoMapper.selectByIdNumber(idNumber);
        if(selectByIdNumber==null||selectByIdNumber.size()==0){
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("通过身份证号获取用户实名制失败：该身份证号未注册用户");
            return resultVo;
        }
        if(null!=selectByIdNumber&&selectByIdNumber.size()>0){
            for(UserInfo userInfo:selectByIdNumber){
                if(userInfo.getIsIdentify()>0&&orgCode.equals(userInfo.getOrgCode())){
                    resultVo.setSuccess(true);
                    resultVo.setResultData(true);//已实名制
                    return resultVo;
                }
            }
            resultVo.setSuccess(true);
            resultVo.setResultData(false);//未实名制
        }
        }
        catch(Exception e){
            LOGGER.error("查询数据库失败",e);
            resultVo.setErrorMsg("获取信息失败");
            return resultVo;
        }
        return resultVo;
    }


    @Override
    public BaseResultVo<UserInfoPo> getUserInfoByUserNameAndOrgCode(String userLoginName, String orgCode) {
        BaseResultVo<UserInfoPo> resultVo = new BaseResultVo<UserInfoPo>();
        LOGGER.info("获取用户信息接口入参：userLoginName{},orgCode{}",userLoginName,orgCode);
        if (StringUtils.isEmpty(userLoginName)||StringUtils.isEmpty(orgCode)) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USER_USERNAME_EMPTY);
            resultVo.setErrorMsg("获取用户信息接口失败 ：用户登录名或者机构号为空");
            return resultVo;
        }
        try{
        UserInfo userInfo = userInfoMapper.selectByLoginName(userLoginName, orgCode);
        UserInfoPo userInfoPo = new UserInfoPo();
        if(null!=userInfo){
                userInfoPo.setUserId(userInfo.getUserId());
                userInfoPo.setUserCode(userInfo.getUserCode());
                resultVo.setSuccess(true);
                resultVo.setResultData(userInfoPo);
            
        }
        else{
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_EXSIT_USERNAME);
            resultVo.setErrorMsg("查询用户信息为空,用户不存在");
            
        }
        }catch(Exception e){
            LOGGER.error("通过登陆名机构号查询用户信息失败",e);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("通过登陆名机构号查询用户信息失败:程序执行异常,"+e.getMessage());
            return resultVo;
        }
        return resultVo;
    }


    @Override
    public BaseResultVo<Boolean> openAccountPerson(OpenAccountPerson openAccountPerson) {
        LOGGER.info("UserCenterServiceImpl.openAccountPerson个人开户入参openAccountPerson：{}",openAccountPerson);
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        try {
            CommonResponse openPersonAccount = accountDubbo.openPersonAccount(openAccountPerson);
            if(ResponseConstants.SUCCESS.equals(openPersonAccount.getCode())){
                resultVo.setSuccess(true);
                LOGGER.info("UserCenterServiceImpl.openAccountPerson个人开户成功");
            }else{
                resultVo.setSuccess(false);
                resultVo.setResponseCode(openPersonAccount.getCode());
                resultVo.setErrorMsg(openPersonAccount.getMsg());
                LOGGER.info("UserCenterServiceImpl.openAccountPerson个人开户失败");
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("UserCenterServiceImpl.openAccountPerson个人开户失败：",e);
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("UserCenterServiceImpl.openAccountPerson个人开户失败,实体对象转换失败,"+e.getMessage());
            return resultVo;
        } catch (InvocationTargetException e) {
            LOGGER.error("UserCenterServiceImpl.openAccountPerson个人开户失败：",e);
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("UserCenterServiceImpl.openAccountPerson个人开户失败,程序执行异常,"+e.getMessage());
            return resultVo;
        }
        
        return resultVo;
    }


    @Override
    public BaseResultVo<Boolean> openAccountCompany(OpenAccountCompany openAccountCompany) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public BaseResultVo<Boolean> bindCard(BindAccountVo bindAccountVo) {
        LOGGER.info("UserCenterServiceImpl.bindCard绑卡入参bindAccountVo：{}",bindAccountVo);
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        
        try {
            CommonResponse bindcard = accountDubbo.bindcard(bindAccountVo);
            if(ResponseConstants.SUCCESS.equals(bindcard.getCode())){
                resultVo.setSuccess(true);
                LOGGER.info("UserCenterServiceImpl.bindCard绑卡成功");
            }else{
                resultVo.setSuccess(false);
                resultVo.setResponseCode(bindcard.getCode());
                resultVo.setErrorMsg(bindcard.getMsg());
                LOGGER.info("UserCenterServiceImplbindCard绑卡失败");
            }
        } catch (Exception e) {
            LOGGER.error("UserCenterServiceImpl.bindCard绑卡失败：",e);
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_RUNEXCEPTION_ERROR);
            resultVo.setErrorMsg("UserCenterServiceImpl.bindCard绑卡失败,程序执行异常,"+e.getMessage());
            return resultVo;
        }
        return resultVo;
    }
}
