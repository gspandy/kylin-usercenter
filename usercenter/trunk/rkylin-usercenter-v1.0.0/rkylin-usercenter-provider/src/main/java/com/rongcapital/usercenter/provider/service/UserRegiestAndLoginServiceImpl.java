package com.rongcapital.usercenter.provider.service;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rkylin.usercenter.util.CaasUserServiceProxy;
import com.rkylin.usercenter.util.vo.LoginResponse;
import com.rkylin.usercenter.util.vo.RegiesterResponse;
import com.rongcapital.usercenter.api.exception.RegiesterException;
import com.rongcapital.usercenter.api.service.UserRegiestAndLoginService;
import com.rongcapital.usercenter.api.vo.AuthResultVo;
import com.rongcapital.usercenter.api.vo.Base64VcodeResponse;
import com.rongcapital.usercenter.api.vo.BaseResultVo;
import com.rongcapital.usercenter.api.vo.CompanyRegiesterInfo;
import com.rongcapital.usercenter.api.vo.LoginInfo;
import com.rongcapital.usercenter.api.vo.LoginInfo.TerminalType;
import com.rongcapital.usercenter.api.vo.LoginResultVo;
import com.rongcapital.usercenter.api.vo.PersonRegiesterInfo;
import com.rongcapital.usercenter.api.vo.UserRegiesterInfo;
import com.rongcapital.usercenter.api.vo.UserRegiesterInfo.RegiesterType;
import com.rongcapital.usercenter.provider.constant.UserCenterErrorCode;
import com.rongcapital.usercenter.provider.dao.CompanyInfoMapper;
import com.rongcapital.usercenter.provider.dao.PersonInfoMapper;
import com.rongcapital.usercenter.provider.dao.UserInfoMapper;
import com.rongcapital.usercenter.provider.dao.UserLoginHistoryMapper;
import com.rongcapital.usercenter.provider.dao.UserLoginMapper;
import com.rongcapital.usercenter.provider.po.CompanyInfo;
import com.rongcapital.usercenter.provider.po.PersonInfo;
import com.rongcapital.usercenter.provider.po.UserInfo;
import com.rongcapital.usercenter.provider.po.UserInfo.Status;
import com.rongcapital.usercenter.provider.po.UserLogin;
import com.rongcapital.usercenter.provider.po.UserLoginHistory;
import com.rongcapital.usercenter.provider.util.CaasConfigPathUtils;
import com.rongcapital.usercenter.provider.util.UserRedisSessionUtils;
import com.ruixue.serviceplatform.commons.exception.NotFoundException;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserRegiestAndLoginServiceImpl implements UserRegiestAndLoginService {

    private String castErrorCode = "E001";
    
    private String unSupportRegiesterTypeErrorCode = "E002";
    
    private String redisOperateErrorCode = "E003";

    private String emptyErrorCode = "E004";
    
    @Autowired
    private CaasUserServiceProxy caasUserServiceProxy;


    @Autowired
    private LocalUserService localUserService;
    
    

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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegiestAndLoginServiceImpl.class);

    private static final Logger REGIEST_LOGGER = LoggerFactory.getLogger("userCenterRegiestLogger");
    
    @Override
    public BaseResultVo<Boolean> regiest(UserRegiesterInfo regiesterInfo) throws RegiesterException {
        // TODO Auto-generated method stub
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        if (null == regiesterInfo) {
            resultVo.setErrorMsg("注册实体不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }else if(StringUtils.isEmpty(regiesterInfo.getAppCode())){
            resultVo.setErrorMsg("appCode不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }else if(null == regiesterInfo.getRegiesterType()){
            resultVo.setErrorMsg("注册类型不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        } else {
            String userName = regiesterInfo.getUserName();
            String password = regiesterInfo.getPassword();  
            String appCode = regiesterInfo.getAppCode();
            String configfile ="";
            try{
                configfile = CaasConfigPathUtils.getConfigfile(appCode);  
            } catch(NotFoundException e){
                resultVo.setSuccess(false);
                resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_APPCODE);
                resultVo.setErrorMsg("应用appcode不存在");
                return resultVo;
            }
            
            LOGGER.debug("传参：userName"+userName+"|password"+password+"|configfile"+configfile);
           
            BaseResultVo<Boolean> validateParam = validateParam(regiesterInfo);
            if (!validateParam.isSuccess()) {
                return validateParam;
            }
            
            UserLogin selectByUserName = userLoginMapper.selectByUserName(userName);
            if(null != selectByUserName){
                LOGGER.debug("从数据库查询到用户名{}已存在",userName);
                resultVo.setSuccess(false);
                resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_EXSIT_USERNAME);
                resultVo.setErrorMsg("用户名已存在");
                return resultVo;
            }
            
            RegiesterType regiesterType = regiesterInfo.getRegiesterType();
            Integer userType = regiesterType.value();
            if (RegiesterType.PERSON == regiesterType) {
                PersonRegiesterInfo person = null;
                try {
                    person = (PersonRegiesterInfo) regiesterInfo;
                } catch (ClassCastException exception) {
                    resultVo.setResponseCode(castErrorCode);
                    resultVo.setErrorMsg("注册个人信息请使用个人注册参数");
                    return resultVo;
                }


                // 校验个人真实姓名
                String cName = person.getUserCname();
                if (StringUtils.isEmpty(cName)) {
                    resultVo.setResponseCode(emptyErrorCode);
                    resultVo.setErrorMsg("个人真实姓名不能为空");
                    return resultVo;
                }
                
                UserInfo userInfo = new UserInfo();
                userInfo.setUserType(userType);
                userInfo.setStatus(Status.SUCCESS.value()); // 状态
                userInfo.setUserId(userName);
                
                PersonInfo personInfo = new PersonInfo();
                
                UserLogin userLogin = new UserLogin();
                userLogin.setLoginName(userName);
                userLogin.setPwdMd(password);
                userLogin.setPwdSalt(password);
                userLogin.setUserType(regiesterType.value());
                
                try {
                    BeanUtils.copyProperties(personInfo, person);
                } catch (Exception e) {
                    LOGGER.error("复制个人信息异常",e);
                    resultVo.setResponseCode(castErrorCode);
                    resultVo.setErrorMsg("个人注册接口异常："+ e.getMessage());
                    return resultVo;
                }
                try{
                    UserInfo userInfoByLoginName = localUserService.getUserInfoByLoginName(userName);    
                    if(userInfoByLoginName!=null){
                        resultVo.setSuccess(false);
                        resultVo.setResponseCode(castErrorCode);
                        resultVo.setErrorMsg("个人注册接口异常：该用户已注册");
                        return resultVo;
                }
                    }
                    catch(Exception e){
                        LOGGER.error("注册个人用户，查询数据库失败,username:{}",userName);
                    }
                localUserService.regiestPersonInfo(userInfo, personInfo, userLogin);
                
                
                
                BaseResultVo<RegiesterResponse> regiest = caasUserServiceProxy.regiest(configfile, regiesterInfo.getAppCode(), userName, password, regiesterInfo.getVcode(), null);
               if(regiest.isSuccess()){
                   RegiesterResponse caasUserCode = regiest.getResultData();
                   localUserService.modifyCaasUserCode(userInfo.getUserInfoId(), caasUserCode.getUserCode());
                   //保存xauthToken
                   try{
                   UserRedisSessionUtils.doRefleshXAuthToken(userName, caasUserCode.getxAuthToken());
                   }
                   catch(Exception e){
                       LOGGER.error("注册redis操作保存xauthToken失败",e);
                       
                   }
                   REGIEST_LOGGER.info("个人用户注册成功，注册信息为：{},CAAS USER_CODE:{}",regiesterInfo,caasUserCode);
                   LOGGER.info("个人用户注册成功，注册信息为：{},CAAS USER_CODE:{}",regiesterInfo,caasUserCode);
                   resultVo.setSuccess(true);
                   resultVo.setResultData(true);
                   return resultVo;
               }else{
                   LOGGER.error("个人用户注册失败：",new RegiesterException(regiest.getResponseCode(), regiest.getErrorMsg()));
                   resultVo.setSuccess(false);
                   resultVo.setErrorMsg(regiest.getErrorMsg());
                   resultVo.setResponseCode(regiest.getResponseCode());
                   return resultVo;
                  
                  // throw new RegiesterException(regiest.getResponseCode(), regiest.getErrorMsg());
               }
                    
            } else if (RegiesterType.COMPANY == regiesterType) {
                CompanyRegiesterInfo company = null;
                try {
                    company = (CompanyRegiesterInfo) regiesterInfo;

                } catch (ClassCastException exception) {
                    resultVo.setResponseCode(castErrorCode);
                    resultVo.setErrorMsg("注册企业信息请使用企业注册参数");
                    return resultVo;
                }
               

                String companyName = company.getCompanyName();
                String buslince = company.getBuslince();
                if (StringUtils.isEmpty(companyName)) {
                    resultVo.setResponseCode(emptyErrorCode);
                    resultVo.setErrorMsg("企业名称不能为空");
                    return resultVo;
                }
                if (StringUtils.isEmpty(buslince)) {
                    resultVo.setResponseCode(emptyErrorCode);
                    resultVo.setErrorMsg("企业营业执照号不能为空");
                    return resultVo;
                }
                

           
                    
                UserInfo userInfo = new UserInfo();
                userInfo.setUserType(userType);
                userInfo.setStatus(Status.SUCCESS.value()); // 状态
                userInfo.setUserId(userName);
                CompanyInfo companyInfo = new CompanyInfo();
                UserLogin userLogin = new UserLogin();
                userLogin.setLoginName(userName);
                userLogin.setPwdMd(password);
                userLogin.setPwdSalt(password);
                userLogin.setUserType(regiesterType.value());
                
                try {
                    BeanUtils.copyProperties(companyInfo, company);
                } catch (Exception e) {
                    LOGGER.error("复制企业信息异常",e);
                    resultVo.setResponseCode(castErrorCode);
                    resultVo.setErrorMsg("企业注册接口异常："+ e.getMessage());
                    return resultVo;
                }
                    
                try{
                UserInfo userInfoByLoginName = localUserService.getUserInfoByLoginName(userName);    
                if(userInfoByLoginName!=null){
                    resultVo.setSuccess(false);
                    resultVo.setResponseCode(castErrorCode);
                    resultVo.setErrorMsg("企业注册接口异常：该企业已注册");
                    return resultVo;
                }
                }
                catch(Exception e){
                    LOGGER.error("注册企业用户，查询数据库失败,username:{}",userName);
                }
                localUserService.regiestCompanyInfo(userInfo, companyInfo, userLogin);     
                
                BaseResultVo<RegiesterResponse> regiest =
                        caasUserServiceProxy.regiest(configfile,appCode , userName, password, regiesterInfo.getVcode(), null);
                
                if(regiest.isSuccess()){
                    
                    RegiesterResponse caasUserCode =  regiest.getResultData();
                    localUserService.modifyCaasUserCode(userInfo.getUserInfoId(), caasUserCode.getUserCode());
                    //保存xauthToken
                    try{
                    UserRedisSessionUtils.doRefleshXAuthToken(userName, caasUserCode.getxAuthToken());
                    }
                    catch(Exception e){
                        LOGGER.error("注册redis操作保存xauthToken失败",e);
                        
                    }
                    REGIEST_LOGGER.info("企业用户注册成功，注册信息为：{},CAAS USER_CODE:{}",regiesterInfo,caasUserCode);
                    LOGGER.info("企业用户注册成功，注册信息为：{},CAAS USER_CODE:{}",regiesterInfo,caasUserCode);
                    resultVo.setSuccess(true);
                    resultVo.setResultData(true);
                    return resultVo;
                }else{
                    LOGGER.error("企业用户注册失败：",new RegiesterException(regiest.getResponseCode(), regiest.getErrorMsg()));
                    resultVo.setSuccess(false);
                    resultVo.setErrorMsg(regiest.getErrorMsg());
                    resultVo.setResponseCode(regiest.getResponseCode());
                    return resultVo;
                // throw new RegiesterException(regiest.getResponseCode(), regiest.getErrorMsg());
                }
                
                    
            }else{
                resultVo.setResponseCode(unSupportRegiesterTypeErrorCode);
                resultVo.setErrorMsg("暂不支持的注册类型");
                return resultVo;
            }
            
        }
    }

    private BaseResultVo<Boolean> validateParam(UserRegiesterInfo regiesterInfo) {

        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        String loginName = regiesterInfo.getUserName();
        String password = regiesterInfo.getPassword();
        if (StringUtils.isEmpty(loginName)) {
            resultVo.setResponseCode(emptyErrorCode);
            resultVo.setErrorMsg("登录名不能为空");
            return resultVo;
        } else if (StringUtils.isEmpty(password)) {
            resultVo.setResponseCode(emptyErrorCode);
            resultVo.setErrorMsg("登录密码不能为空");
            return resultVo;
        }
        resultVo.setSuccess(true);
        return resultVo;
    }

    @Override
    public LoginResultVo<String> login(LoginInfo loginInfo) {
        
        LoginResultVo<String> resultVo = new LoginResultVo<String>();
        
        if (null == loginInfo) {
            resultVo.setResponseCode(emptyErrorCode);
            resultVo.setErrorMsg("登录信息实体不能为空");
            return resultVo;
        } else {


            String appCode = loginInfo.getAppCode();
            TerminalType terminalType = loginInfo.getTerminalType();
            String loginName = loginInfo.getLoginName();
            String password = loginInfo.getPassword();
            if(StringUtils.isEmpty(appCode)){
                resultVo.setResponseCode(emptyErrorCode);
                resultVo.setErrorMsg("appCode不能为空");
                return resultVo;
            }else if(null == terminalType){
                resultVo.setResponseCode(emptyErrorCode);
                resultVo.setErrorMsg("终端类型不能为空");
                return resultVo;
            }else if(StringUtils.isEmpty(loginName)){
                resultVo.setResponseCode(emptyErrorCode);
                resultVo.setErrorMsg("登录名不能为空");
                return resultVo;
            }else if (StringUtils.isEmpty(password)) {
                resultVo.setResponseCode(emptyErrorCode);
                resultVo.setErrorMsg("登录密码不能为空");
                return resultVo;
            }
            
            UserInfo userInfo = this.userInfoMapper.selectByUserId(loginName);
            if(userInfo==null){
                resultVo.setSuccess(false);
                resultVo.setResponseCode(emptyErrorCode);
                resultVo.setErrorMsg("用户未注册或者已删除");
                return resultVo;
            }
            userInfo.setLastLoginTime(new Date());
            UserLoginHistory userLoginHistory = new UserLoginHistory();
            try{
                userLoginHistory.setLoginIp(loginInfo.getTerminalNo());
                userLoginHistory.setLoginType(1);
                userLoginHistory.setLongSource(Integer.valueOf(terminalType.toString()));
                if(null != userInfo){
                    
                    userLoginHistory.setUserInfoId(userInfo.getUserInfoId());
                }
                userLoginHistoryMapper.insertSelective(userLoginHistory );
            }catch(Exception e){
                LOGGER.warn("插入用户登录历史信息发生异常{}",userLoginHistory,e);
            }
           String configfile ="";
           try{
               configfile = CaasConfigPathUtils.getConfigfile(appCode);  
           }
           catch(NotFoundException e){
               resultVo.setSuccess(false);
               resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_APPCODE);
               resultVo.setErrorMsg("应用appcode不存在");
               return resultVo;
           }
            String vcode = loginInfo.getVcode();
           // String xAuthToken = loginInfo.getxAuthToken();
            String xAuthToken = null;
            try {
                xAuthToken = UserRedisSessionUtils.getXAuthToken(loginName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                LOGGER.error("get xauthtoken from redis error",e);
            }
            BaseResultVo<LoginResponse> login = this.caasUserServiceProxy.login(configfile, appCode, loginName, password, vcode, xAuthToken);
            
            LoginResponse loginResponse = login.getResultData();
            if(login.isSuccess()){
                LOGGER.debug("appCode:{},terminalType:{},登录成功，authCode:{}",appCode,terminalType,loginResponse.getAuthCode());
                BaseResultVo<AuthResultVo> userAuth = this.caasUserServiceProxy.userAuth(configfile,appCode,loginResponse.getAuthCode());
                AuthResultVo authResultVo = userAuth.getResultData();
                String userToken = UUID.randomUUID().toString();
                if(userAuth.isSuccess()){
                    
                    String accessToken = authResultVo.getAccessToken();
                    LOGGER.debug("登录接口调用授权接口进行redis记录操作,appCode:{},terminalType:{},authCode:{},accessToken:{}",appCode,terminalType,loginResponse.getAuthCode(),accessToken);
                    resultVo.setResultData(userToken);
                    try{
                        //移动设备和PC不共享
                        UserRedisSessionUtils.doUserLoginInfo(appCode, terminalType, userToken, userInfo, authResultVo);
                        UserRedisSessionUtils.doRefleshXAuthToken(loginName, xAuthToken);
                        resultVo.setSuccess(true);
                    }catch(Exception e){
                        LOGGER.warn("登录插入redis数据发生异常",e);
                        resultVo.setSuccess(false);
                        resultVo.setResponseCode(redisOperateErrorCode);
                        resultVo.setErrorMsg("系统发生异常，请稍后重试");
                        return resultVo;
                    }
                }
                return resultVo;
            }else{
                resultVo.setSuccess(false);
                resultVo.setFaildCount(loginResponse.getRetryTimes());
                resultVo.setErrorMsg(login.getErrorMsg());
                resultVo.setResponseCode(login.getResponseCode());
                return resultVo;
            }
        
        }
    }

    @Override
    public BaseResultVo<Base64VcodeResponse> getBase64Vcode(String appCode, String xAuthToken) {
        BaseResultVo<Base64VcodeResponse> resultVo = new BaseResultVo<Base64VcodeResponse>();
        if(StringUtils.isEmpty(appCode)){
            resultVo.setErrorMsg("appCode不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }
        String configfile = CaasConfigPathUtils.getConfigfile(appCode);
        return this.caasUserServiceProxy.getBase64Vcode(configfile, appCode, xAuthToken);
    }

}
