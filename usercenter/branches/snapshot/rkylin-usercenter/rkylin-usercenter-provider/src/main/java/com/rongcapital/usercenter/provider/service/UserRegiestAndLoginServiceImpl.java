package com.rongcapital.usercenter.provider.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StopWatch;

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
import com.rongcapital.usercenter.api.vo.ResponseResultVo;
import com.rongcapital.usercenter.api.vo.UserRegiesterInfo;
import com.rongcapital.usercenter.api.vo.UserRegiesterInfo.RegiesterType;
import com.rongcapital.usercenter.api.vo.WeChatRegisterInfo;
import com.rongcapital.usercenter.provider.constant.UserCenterErrorCode;
import com.rongcapital.usercenter.provider.dao.CompanyInfoMapper;
import com.rongcapital.usercenter.provider.dao.PersonInfoMapper;
import com.rongcapital.usercenter.provider.dao.UcOrgMappingMapper;
import com.rongcapital.usercenter.provider.dao.UcSyetemConfigMapper;
import com.rongcapital.usercenter.provider.dao.UserInfoMapper;
import com.rongcapital.usercenter.provider.dao.UserLoginHistoryMapper;
import com.rongcapital.usercenter.provider.dao.UserLoginMapper;
import com.rongcapital.usercenter.provider.po.CompanyInfo;
import com.rongcapital.usercenter.provider.po.PersonInfo;
import com.rongcapital.usercenter.provider.po.UcOrgMapping;
import com.rongcapital.usercenter.provider.po.UcSyetemConfig;
import com.rongcapital.usercenter.provider.po.UserInfo;
import com.rongcapital.usercenter.provider.po.UserInfo.Status;
import com.rongcapital.usercenter.provider.po.UserLogin;
import com.rongcapital.usercenter.provider.po.UserLoginHistory;
import com.rongcapital.usercenter.provider.util.CaasConfigPathUtils;
import com.rongcapital.usercenter.provider.util.EncryptionUtil;
import com.rongcapital.usercenter.provider.util.GenertaeIDUtil;
import com.rongcapital.usercenter.provider.util.SaltUtil;
import com.rongcapital.usercenter.provider.util.UserCenterConfig;
import com.rongcapital.usercenter.provider.util.UserRedisSessionUtils;
import com.ruixue.serviceplatform.commons.exception.NotFoundException;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserRegiestAndLoginServiceImpl implements UserRegiestAndLoginService {

    private String castErrorCode = "E001";

    private String unSupportRegiesterTypeErrorCode = "E002";

    private String redisOperateErrorCode = "E003";

    private String emptyErrorCode = "E004";

    public static final String ERRORPWDEXPIRETIME = UserCenterConfig.getProperty("uc.config.almond.errorpwd.key");

    public static final String WECHATAPPCODE = "wechat";
    
    public static final String CAAS_PWD_ERROR_CODE="E9021";

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

    @Autowired
    private UcSyetemConfigMapper ucSyetemConfig;
    
    @Autowired
    private UcOrgMappingMapper ucOrgMappingMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegiestAndLoginServiceImpl.class);

    private static final Logger REGIEST_LOGGER = LoggerFactory.getLogger("userCenterRegiestLogger");
   
    private static final String MECHANT_ACTUAL_ORGCODE2 = UserCenterConfig.getProperty("uc.actual.orgcode.mechant2");

    @Override
    public BaseResultVo<ResponseResultVo> regiest(UserRegiesterInfo regiesterInfo) throws RegiesterException {
        BaseResultVo<ResponseResultVo> resultVo = new BaseResultVo<ResponseResultVo>();
        StopWatch sw = new StopWatch();
        sw.start("本地方法数据库操作耗时");
        ResponseResultVo vo = new ResponseResultVo();
        if (null == regiesterInfo) {
            resultVo.setErrorMsg("注册实体不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        } else if (StringUtils.isEmpty(regiesterInfo.getAppCode())) {
            resultVo.setErrorMsg("appCode不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        } else if (null == regiesterInfo.getRegiesterType()) {
            resultVo.setErrorMsg("注册类型不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        } else {
            String userName = regiesterInfo.getUserName();
            String passwordParame = regiesterInfo.getPassword();
            String appCode = regiesterInfo.getAppCode();
            String orgCode = regiesterInfo.getOrgCode();
            String userId = regiesterInfo.getUserId();
            /*-----密码处理------*/
            String salt = null;
            try {
                salt = SaltUtil.createSalt();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                LOGGER.error("密码加盐", e);
            }
            String password = EncryptionUtil.sha256Encry(passwordParame, salt);

            String configfile = "";
            if (!StringUtils.isEmpty(userId)) {// userid不为空为老数据注册，保证本机构唯一
                UserInfo selectByUserId = userInfoMapper.selectByUserId(userId, orgCode);
                if (null != selectByUserId) {
                    LOGGER.debug("userid{},机构{}已存在", userId, orgCode);
                    resultVo.setSuccess(false);
                    resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_EXSIT_USERNAME);
                    resultVo.setErrorMsg("当前机构下用户ID已存在");
                    return resultVo;
                }
            } else {
                userId = GenertaeIDUtil.generateUserId(regiesterInfo.getRegiesterType());
            }

            // userId=StringUtils.isEmpty(userId)?GenertaeIDUtil.generateUserId(regiesterInfo.getRegiesterType()):userId;
            String userCode = GenertaeIDUtil.generateUserCode(regiesterInfo.getRegiesterType());
            try {

                configfile = CaasConfigPathUtils.getConfigfile(appCode);
            } catch (NotFoundException e) {
                resultVo.setSuccess(false);
                resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_APPCODE);
                resultVo.setErrorMsg("应用appcode不存在");
                return resultVo;
            }

            LOGGER.debug("传参：userName:{},orgCode:{},password:{},configfile:{}", userName, orgCode, password, configfile);

            BaseResultVo<ResponseResultVo> validateParam = validateParam(regiesterInfo);
            if (!validateParam.isSuccess()) {
                return validateParam;
            }

            UserLogin selectByUserName = userLoginMapper.selectByUserName(userName, orgCode);
            if (null != selectByUserName) {
                LOGGER.debug("从数据库查询到用户名{},机构{}已存在", userName, orgCode);
                resultVo.setSuccess(false);
                resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_EXSIT_USERNAME);
                resultVo.setErrorMsg("当前机构下用户名已存在");
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
                // String cName = person.getUserCname();
                // if (StringUtils.isEmpty(cName)) {
                // resultVo.setResponseCode(emptyErrorCode);
                // resultVo.setErrorMsg("个人真实姓名不能为空");
                // return resultVo;
                // }
                //
                UserInfo userInfo = new UserInfo();
                userInfo.setUserType(userType);
                userInfo.setStatus(Status.SUCCESS.value()); // 状态
                userInfo.setUserId(userId);
                userInfo.setUserCode(userCode);
                userInfo.setOrgCode(orgCode);
                userInfo.setLoginName(userName);
                if (!StringUtils.isEmpty(person.getIdNumber())) {// 若身份证不为空，设置
                    userInfo.setIdCardNumber(person.getIdNumber());
                }
                PersonInfo personInfo = new PersonInfo();

                UserLogin userLogin = new UserLogin();
                userLogin.setLoginName(userName);
                userLogin.setPwdMd(password);
                userLogin.setPwdSalt(salt);
                userLogin.setOrgCode(orgCode);
                userLogin.setUserType(regiesterType.value());

                try {
                    BeanUtils.copyProperties(personInfo, person);
                } catch (Exception e) {
                    LOGGER.error("复制个人信息异常", e);
                    resultVo.setResponseCode(castErrorCode);
                    resultVo.setErrorMsg("个人注册接口异常：" + e.getMessage());
                    return resultVo;
                }
                try {
                    UserInfo userInfoByLoginName = localUserService.getUserInfoByLoginName(userName, orgCode);

                    if (!StringUtils.isEmpty(person.getIdNumber())) {// 若身份证不为空，设置
                        List<UserInfo> selectByIdNumber = userInfoMapper.selectByIdNumber(person.getIdNumber());
                        if (null != selectByIdNumber && selectByIdNumber.size() > 0) {
                            for (UserInfo userinfo : selectByIdNumber) {
                                if (null != userinfo && orgCode.equals(userinfo.getOrgCode())
                                        && person.getIdNumber().equals(userinfo.getIdCardNumber())) {// 注册身份证在该机构下已存在
                                    resultVo.setResponseCode(castErrorCode);
                                    resultVo.setErrorMsg("个人注册接口异常：该用户身份证在当前机构已被注册");
                                    return resultVo;
                                }

                            }
                        }
                    }

                    if (userInfoByLoginName != null) {
                        resultVo.setSuccess(false);
                        resultVo.setResponseCode(castErrorCode);
                        resultVo.setErrorMsg("个人注册接口异常：该用户已注册");
                        return resultVo;
                    }
                } catch (Exception e) {
                    LOGGER.error("注册个人用户，查询数据库失败,username:{}", userName);
                    resultVo.setSuccess(false);
                    resultVo.setResponseCode(castErrorCode);
                    resultVo.setErrorMsg("个人注册接口异常：请重新注册");
                    return resultVo;

                }
                localUserService.regiestPersonInfo(userInfo, personInfo, userLogin);

                sw.stop();

                sw.start("调用 caas注册耗时");
                try {
                    BaseResultVo<RegiesterResponse> regiest =
                            caasUserServiceProxy.regiest(configfile, regiesterInfo.getAppCode(), userName + orgCode,
                                    password, regiesterInfo.getVcode(), null);
                    if (regiest.isSuccess()) {
                        RegiesterResponse caasUserCode = regiest.getResultData();
                        localUserService.modifyCaasUserCode(userInfo.getUserInfoId(), caasUserCode.getUserCode());
                        // 保存xauthToken
                        try {
                           // regiest.getResultData().getUserCode();
                            UserRedisSessionUtils.doRefleshXAuthToken(userName,orgCode, caasUserCode.getxAuthToken());
                        } catch (Exception e) {
                            LOGGER.error("注册redis操作保存xauthToken失败", e);

                        }
                        REGIEST_LOGGER.info("个人用户注册成功，注册信息为：{},CAAS USER_CODE:{}", regiesterInfo, caasUserCode);
                        LOGGER.info("个人用户注册成功，注册信息为：{},CAAS USER_CODE:{}", regiesterInfo, caasUserCode);
                        resultVo.setSuccess(true);
                        vo.setUserId(userId);
                        vo.setUserCode(userCode);
                        resultVo.setResultData(vo);
                        sw.stop();
                        LOGGER.info("个人注册耗时" + sw.prettyPrint());
                        return resultVo;
                    } else {
                        LOGGER.error("个人用户注册失败：",
                                new RegiesterException(regiest.getResponseCode(), regiest.getErrorMsg()));
                        resultVo.setSuccess(false);
                        resultVo.setErrorMsg(regiest.getErrorMsg());
                        resultVo.setResponseCode(regiest.getResponseCode());
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return resultVo;

                        // throw new RegiesterException(regiest.getResponseCode(), regiest.getErrorMsg());
                    }
                } catch (Exception e) {
                    LOGGER.error("个人用户注册失败：", e);
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    resultVo.setSuccess(false);
                    resultVo.setErrorMsg("个人用户注册失败：" + e.getMessage());
                    resultVo.setResponseCode(castErrorCode);
                    return resultVo;

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
                userInfo.setUserId(userId);
                userInfo.setOrgCode(orgCode);
                userInfo.setUserCode(userCode);
                userInfo.setIdCardNumber(buslince);//营业执照号
                userInfo.setLoginName(userName);

                CompanyInfo companyInfo = new CompanyInfo();
                UserLogin userLogin = new UserLogin();
                userLogin.setLoginName(userName);
                company.setOrgCode(orgCode);

                userLogin.setOrgCode(orgCode);
                userLogin.setPwdMd(password);
                userLogin.setPwdSalt(salt);
                userLogin.setUserType(regiesterType.value());

                try {
                    BeanUtils.copyProperties(companyInfo, company);
                } catch (Exception e) {
                    LOGGER.error("复制企业信息异常", e);
                    resultVo.setResponseCode(castErrorCode);
                    resultVo.setErrorMsg("企业注册接口异常：" + e.getMessage());
                    return resultVo;
                }

                try {
                    UserInfo userInfoByLoginName = localUserService.getUserInfoByLoginName(userName, orgCode);
                    if (userInfoByLoginName != null) {
                        resultVo.setSuccess(false);
                        resultVo.setResponseCode(castErrorCode);
                        resultVo.setErrorMsg("企业注册接口异常：该企业已注册");
                        return resultVo;
                    }
                        // 若身份证不为空，设置
                        List<UserInfo> selectByIdNumber = userInfoMapper.selectByIdNumber(buslince);
                        if (null != selectByIdNumber && selectByIdNumber.size() > 0) {
                            for (UserInfo userinfo : selectByIdNumber) {
                                if (null != userinfo && orgCode.equals(userinfo.getOrgCode())
                                        ) {// 注册营业执照号在该机构下已存在
                                    resultVo.setResponseCode(castErrorCode);
                                    resultVo.setErrorMsg("企业注册接口异常：该企业营业执照在当前机构已被注册");
                                    return resultVo;
                                }

                            }
                        }
                    localUserService.regiestCompanyInfo(userInfo, companyInfo, userLogin);
                } catch (Exception e) {
                    LOGGER.error("注册企业用户，查询数据库失败,username:{}", userName);
                    resultVo.setSuccess(false);
                    resultVo.setResponseCode(castErrorCode);
                    resultVo.setErrorMsg("企业注册接口异常：请重试");
                    return resultVo;
                }

                sw.stop();
                sw.start("调用 caas注册耗时");
                try{
                
                BaseResultVo<RegiesterResponse> regiest =
                        caasUserServiceProxy.regiest(configfile, appCode, userName + orgCode, password,
                                regiesterInfo.getVcode(), null);

                if (regiest.isSuccess()) {

                    RegiesterResponse caasUserCode = regiest.getResultData();
                    localUserService.modifyCaasUserCode(userInfo.getUserInfoId(), caasUserCode.getUserCode());
                    // 保存xauthToken
                    try {
                        UserRedisSessionUtils.doRefleshXAuthToken(userName,orgCode, caasUserCode.getxAuthToken());
                    } catch (Exception e) {
                        LOGGER.error("注册redis操作保存xauthToken失败", e);

                    }
                    REGIEST_LOGGER.info("企业用户注册成功，注册信息为：{},CAAS USER_CODE:{}", regiesterInfo, caasUserCode);
                    LOGGER.info("企业用户注册成功，注册信息为：{},CAAS USER_CODE:{}", regiesterInfo, caasUserCode);
                    resultVo.setSuccess(true);
                    vo.setUserId(userId);
                    vo.setUserCode(userCode);
                    resultVo.setResultData(vo);
                    sw.stop();
                    LOGGER.info("个人注册耗时" + sw.prettyPrint());
                    return resultVo;
                } else {
                    LOGGER.error("企业用户注册失败：", new RegiesterException(regiest.getResponseCode(), regiest.getErrorMsg()));
                    resultVo.setSuccess(false);
                    resultVo.setErrorMsg(regiest.getErrorMsg());
                    resultVo.setResponseCode(regiest.getResponseCode());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return resultVo;
                    // throw new RegiesterException(regiest.getResponseCode(), regiest.getErrorMsg());
                }
                
                
            }catch (Exception e) {
                LOGGER.error("企业用户注册失败：", e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                resultVo.setSuccess(false);
                resultVo.setErrorMsg("企业用户注册失败：" + e.getMessage());
                resultVo.setResponseCode(castErrorCode);
                return resultVo;

            }

            }

            else {
                resultVo.setSuccess(false);
                resultVo.setResponseCode(unSupportRegiesterTypeErrorCode);
                resultVo.setErrorMsg("暂不支持的注册类型");
                return resultVo;
            }

        }
    }

    private BaseResultVo<ResponseResultVo> validateParam(UserRegiesterInfo registerInfo) {

        BaseResultVo<ResponseResultVo> resultVo = new BaseResultVo<ResponseResultVo>();
        String loginName = registerInfo.getUserName();
        String password = registerInfo.getPassword();
        String orgCode = registerInfo.getOrgCode();
        if (StringUtils.isEmpty(orgCode)) {
            resultVo.setResponseCode(emptyErrorCode);
            resultVo.setErrorMsg("机构不能为空");
            return resultVo;
        }
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
    public LoginResultVo<ResponseResultVo> login(LoginInfo loginInfo) {
        LOGGER.info("登陆入参：loginInfo{}", loginInfo);
        StopWatch sw = new StopWatch();
        sw.start("本地方法数据库操作耗时");
        LoginResultVo<ResponseResultVo> resultVo = new LoginResultVo<ResponseResultVo>();
        ResponseResultVo responseResult = new ResponseResultVo();

        if (null == loginInfo) {
            resultVo.setResponseCode(emptyErrorCode);
            resultVo.setErrorMsg("登录信息实体不能为空");
            return resultVo;
        } else {

            String appCode = loginInfo.getAppCode();
            TerminalType terminalType = loginInfo.getTerminalType();
            String loginName = loginInfo.getLoginName();
            String passwordParame = loginInfo.getPassword();
            String orgCode = loginInfo.getOrgCode();

            if (StringUtils.isEmpty(appCode)) {
                resultVo.setResponseCode(emptyErrorCode);
                resultVo.setErrorMsg("appCode不能为空");
                return resultVo;
            } else if (null == terminalType) {
                resultVo.setResponseCode(emptyErrorCode);
                resultVo.setErrorMsg("终端类型不能为空");
                return resultVo;
            } else if (StringUtils.isEmpty(loginName)) {
                resultVo.setResponseCode(emptyErrorCode);
                resultVo.setErrorMsg("登录名不能为空");
                return resultVo;
            } else if (StringUtils.isEmpty(orgCode)) {
                resultVo.setResponseCode(emptyErrorCode);
                resultVo.setErrorMsg("机构不能为空");
                return resultVo;
            } else if (StringUtils.isEmpty(passwordParame)) {
                resultVo.setResponseCode(emptyErrorCode);
                resultVo.setErrorMsg("登录密码不能为空");
                return resultVo;
            }

            UserInfo userInfo = this.userInfoMapper.selectByLoginName(loginName, orgCode);
            if (userInfo == null) {
                resultVo.setSuccess(false);
                resultVo.setResponseCode(emptyErrorCode);
                resultVo.setErrorMsg("用户未注册或者已删除");
                return resultVo;
            }
            String password = "";
            userInfo.setLastLoginTime(new Date());
            userInfo.setLoginName(loginName);
            UserLoginHistory userLoginHistory = new UserLoginHistory();
            try {
                userLoginHistory.setLoginIp(loginInfo.getTerminalNo());
                userLoginHistory.setLoginType(1);
                userLoginHistory.setLongSource(Integer.valueOf(terminalType.toString()));
                UserLogin selectByPrimaryKey = userLoginMapper.selectByPrimaryKey(userInfo.getUserInfoId());
                password = EncryptionUtil.sha256Encry(passwordParame, selectByPrimaryKey.getPwdSalt());
                if (null != userInfo) {

                    userLoginHistory.setUserInfoId(userInfo.getUserInfoId());
                }
                userLoginHistoryMapper.insertSelective(userLoginHistory);
            } catch (Exception e) {
                LOGGER.warn("插入用户登录历史信息发生异常{}", userLoginHistory, e);
            }
            String configfile = "";
            try {
                configfile = CaasConfigPathUtils.getConfigfile(appCode);
            } catch (NotFoundException e) {
                resultVo.setSuccess(false);
                resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_APPCODE);
                resultVo.setErrorMsg("应用appcode不存在");
                return resultVo;
            }
            String vcode = loginInfo.getVcode();
            // String xAuthToken = loginInfo.getxAuthToken();
            String xAuthToken = null;
            try {
                xAuthToken = UserRedisSessionUtils.getXAuthToken(loginName,orgCode);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                LOGGER.error("get xauthtoken from redis error", e);
            }
            sw.stop();
            sw.start("调用caas登陆接口");
            try{
            BaseResultVo<LoginResponse> login =
                    this.caasUserServiceProxy.login(configfile, appCode, loginName + orgCode, password, vcode,
                            xAuthToken);

            LoginResponse loginResponse = login.getResultData();
            if (login.isSuccess()) {
                LOGGER.debug("appCode:{},terminalType:{},登录成功，authCode:{}", appCode, terminalType,
                        loginResponse.getAuthCode());
                BaseResultVo<AuthResultVo> userAuth =
                        this.caasUserServiceProxy.userAuth(configfile, appCode, loginResponse.getAuthCode());
                AuthResultVo authResultVo = userAuth.getResultData();
                String userToken = UUID.randomUUID().toString();
                if (userAuth.isSuccess()) {
                    try {
                        // 若登陆成功,归零redis保存的密码登陆失败次数
                        if (UserRedisSessionUtils.isLoginKeyExsit(loginName, orgCode)) {
                            UserRedisSessionUtils.delLoginKey(loginName, orgCode);
                        }
                        //删除之前登陆产生的userToken相关信息
                        UserRedisSessionUtils.delUserTokenByNameAndTerminal(loginName, orgCode, terminalType);
                        UserRedisSessionUtils.setUserNameAndUserToken(loginName, orgCode, userToken,authResultVo.getExpiresIn());
                       
                    } catch (Exception e) {
                        LOGGER.error("归零redis失败次数失败", e);
                    }

                    String accessToken = authResultVo.getAccessToken();
                    responseResult.setUserId(userInfo.getUserId());
                    responseResult.setUserToken(userToken);
                    LOGGER.debug("登录接口调用授权接口进行redis记录操作,appCode:{},terminalType:{},authCode:{},accessToken:{}",
                            appCode, terminalType, loginResponse.getAuthCode(), accessToken);
                    resultVo.setResultData(responseResult);
                    try {
                        // 移动设备和PC不共享
                        UserRedisSessionUtils.doUserLoginInfo(appCode, terminalType, userToken, userInfo, authResultVo);
                        UserRedisSessionUtils.doRefleshXAuthToken(loginName,orgCode, xAuthToken);
                        resultVo.setSuccess(true);
                    } catch (Exception e) {
                        LOGGER.warn("登录插入redis数据发生异常", e);
                        resultVo.setSuccess(false);
                        resultVo.setResponseCode(redisOperateErrorCode);
                        resultVo.setErrorMsg("系统发生异常，请稍后重试");
                        return resultVo;
                    }
                }else{
                    resultVo.setSuccess(false);
                    resultVo.setResponseCode(redisOperateErrorCode);
                    resultVo.setErrorMsg("登录失败，该应用未授权");
                    return resultVo; 
                }
                sw.stop();
                LOGGER.info("用户中心登陆耗时" + sw.prettyPrint());
                return resultVo;
            } else {
                try {
                    // 设置密码登陆失败次数  通过CAAS登陆返回码判定 变了？
                   
                    if (CAAS_PWD_ERROR_CODE.equals(login.getResponseCode())) { 
                        int expireTime = 0;
                        if (UserRedisSessionUtils.isSystemConfigKeyExsit(ERRORPWDEXPIRETIME)) {//redis读取系统配置  统计多长时间连续输错密码
                            expireTime =
                                    Integer.parseInt(UserRedisSessionUtils.getSystemConfigRedis(ERRORPWDEXPIRETIME));
                        }
                        else {//redis没有从数据库取并且存入redis
                            UcSyetemConfig selectByConfig = ucSyetemConfig.selectByConfig(ERRORPWDEXPIRETIME);
                            expireTime = Integer.parseInt(selectByConfig.getConfigValue());
                            UserRedisSessionUtils.setSystemConfigRedis(ERRORPWDEXPIRETIME,
                                    selectByConfig.getConfigValue());
                        }
                        if (UserRedisSessionUtils.isLoginKeyExsit(loginName, orgCode)) {
                            int count = Integer.parseInt(UserRedisSessionUtils.getErrorLoginTime(loginName, orgCode));
                            UserRedisSessionUtils.setErrorLoginTime(loginName, orgCode, count + 1,0);//过期时间从redis取
                        }
                        else{// 第一次设置key过期时间
                            UserRedisSessionUtils.setErrorLoginTime(loginName, orgCode, 1,expireTime);
                          //  UserRedisSessionUtils.setLoginKeyExpireTime(loginName, orgCode, expireTime);
                        }

                    }
                } catch (Exception e) {
                    LOGGER.error("设置redis失败次数失败", e);
                }
                resultVo.setSuccess(false);
                resultVo.setFaildCount(loginResponse.getRetryTimes());
                resultVo.setErrorMsg(login.getErrorMsg());
                resultVo.setResponseCode(login.getResponseCode());
                sw.stop();
                LOGGER.info("用户中心登陆耗时" + sw.prettyPrint());
                return resultVo;
            }
            }catch(Exception e){
                LOGGER.info("登陆异常，程序处理失败",e);
                resultVo.setSuccess(false);
                resultVo.setErrorMsg("登陆异常，程序处理失败");
                sw.stop();
                return resultVo;
            }
        }
    }

    @Override
    public BaseResultVo<Base64VcodeResponse> getBase64Vcode(String appCode, String xAuthToken) {
        BaseResultVo<Base64VcodeResponse> resultVo = new BaseResultVo<Base64VcodeResponse>();
        if (StringUtils.isEmpty(appCode)) {
            resultVo.setErrorMsg("appCode不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }
        String configfile = CaasConfigPathUtils.getConfigfile(appCode);
        return this.caasUserServiceProxy.getBase64Vcode(configfile, appCode, xAuthToken);
    }

    @Override
    public BaseResultVo<ResponseResultVo> weChatRegist(WeChatRegisterInfo weChatRegiesterInfo)
            throws RegiesterException {
        BaseResultVo<ResponseResultVo> resultVo = new BaseResultVo<ResponseResultVo>();
        if (null == weChatRegiesterInfo) {
            resultVo.setErrorMsg("注册实体不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }
        String weChatId = weChatRegiesterInfo.getWeChatId();
        String publicNumberId = weChatRegiesterInfo.getPublicNumberId();
        String openId = weChatRegiesterInfo.getOpenId();
        String orgCode = weChatRegiesterInfo.getOrgCode();
        String configfile = "";
        String password =
                StringUtils.isEmpty(weChatRegiesterInfo.getPassword()) ? UUID.randomUUID().toString()
                        : weChatRegiesterInfo.getPassword();
        if (StringUtils.isEmpty(weChatId)) {
            resultVo.setErrorMsg("微信号不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }
        if (StringUtils.isEmpty(publicNumberId)) {
            resultVo.setErrorMsg("微信公众号不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }
        if (StringUtils.isEmpty(openId)) {
            resultVo.setErrorMsg("openId不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }
        if (StringUtils.isEmpty(orgCode)) {
            resultVo.setErrorMsg("机构号不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }
        if (StringUtils.isEmpty(weChatId)) {
            resultVo.setErrorMsg("微信号不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }
        if (StringUtils.isEmpty(weChatId)) {
            resultVo.setErrorMsg("微信号不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }

        String userId =
                StringUtils.isEmpty(weChatRegiesterInfo.getUserId()) ? GenertaeIDUtil
                        .generateUserId(RegiesterType.WECHAT) : weChatRegiesterInfo.getUserId();
        String userCode = GenertaeIDUtil.generateUserCode(RegiesterType.WECHAT);
        // 微信号，公众号，openId作为用户登录名
        String userName = weChatId + publicNumberId + openId;

        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(3);
        userInfo.setStatus(Status.SUCCESS.value()); // 状态
        userInfo.setUserId(userId);
        userInfo.setOrgCode(orgCode);
        userInfo.setUserCode(userCode);
        userInfo.setLoginName(userName);

        UserLogin userLogin = new UserLogin();
        userLogin.setLoginName(userName);

        userLogin.setOrgCode(orgCode);
        userLogin.setPwdMd(password);
        userLogin.setPwdSalt(UUID.randomUUID().toString().substring(0, 31));
        userLogin.setUserType(3);

        try {

            configfile = CaasConfigPathUtils.getConfigfile(WECHATAPPCODE);
        } catch (NotFoundException e) {
            resultVo.setSuccess(false);
            resultVo.setResponseCode(UserCenterErrorCode.USERCENTER_NOTEXSIT_APPCODE);
            resultVo.setErrorMsg("应用appcode不存在");
            return resultVo;
        }

        try {
            UserInfo userInfoByLoginName = localUserService.getUserInfoByLoginName(userName, orgCode);
            if (userInfoByLoginName != null) {
                resultVo.setSuccess(false);
                resultVo.setResponseCode(castErrorCode);
                resultVo.setErrorMsg("微信注册接口异常：该微信号+微信公众号+openId匹配用户已注册");
                return resultVo;
            }
            localUserService.regiestOtherInfo(userInfo, userLogin);
        } catch (Exception e) {
            LOGGER.error("微信注册接口异常：该微信号+微信公众号+openId匹配用户已注册,username:{}", userName,e);
            resultVo.setSuccess(false);
            resultVo.setResponseCode(castErrorCode);
            resultVo.setErrorMsg("微信注册接口异常：程序处理失败");
            return resultVo;
        }

        BaseResultVo<RegiesterResponse> regiest =
                caasUserServiceProxy.regiest(configfile, WECHATAPPCODE, userName + orgCode, password,
                        weChatRegiesterInfo.getVcode(), null);

        if (regiest.isSuccess()) {

            RegiesterResponse caasUserCode = regiest.getResultData();
            localUserService.modifyCaasUserCode(userInfo.getUserInfoId(), caasUserCode.getUserCode());
            // 保存xauthToken
            try {
                UserRedisSessionUtils.doRefleshXAuthToken(userName,orgCode, caasUserCode.getxAuthToken());
            } catch (Exception e) {
                LOGGER.error("注册redis操作保存xauthToken失败", e);

            }
            LOGGER.info("微信用户注册成功，注册信息为：{},CAAS USER_CODE:{}", weChatRegiesterInfo.toString(), caasUserCode);
            resultVo.setSuccess(true);
            ResponseResultVo vo = new ResponseResultVo();
            vo.setUserId(userId);
            vo.setUserCode(userCode);
            resultVo.setResultData(vo);
            return resultVo;
        } else {
            LOGGER.error("微信用户注册失败：", new RegiesterException(regiest.getResponseCode(), regiest.getErrorMsg()));
            resultVo.setSuccess(false);
            resultVo.setErrorMsg(regiest.getErrorMsg());
            resultVo.setResponseCode(regiest.getResponseCode());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultVo;

            // throw new RegiesterException(regiest.getResponseCode(), regiest.getErrorMsg());
        }
    }

    @Override
    public BaseResultVo<ResponseResultVo> regiestMerchant(UserRegiesterInfo regiesterInfo, String actualOrgCode,String mechantName)
            throws RegiesterException {
        LOGGER.info("商户注册开始。。。。。。。。。actualOrgCode:{},registdata:{}",actualOrgCode,regiesterInfo);
        BaseResultVo<ResponseResultVo> resultVo = new BaseResultVo<ResponseResultVo>();
        if (StringUtils.isEmpty(actualOrgCode)) {
            resultVo.setErrorMsg("实际机构号不能为空");
            resultVo.setResponseCode(emptyErrorCode);
            return resultVo;
        }
        //当注册对象为 商户时，登陆名在商户平台唯一 
        //当注册对象为商户内部个人/企业，登陆名+商户所属机构唯一，即个人/企业可以在多个商户下注册
        if(MECHANT_ACTUAL_ORGCODE2.equals(regiesterInfo.getOrgCode())){
            regiesterInfo.setOrgCode(MECHANT_ACTUAL_ORGCODE2+mechantName);//虚拟机构加实际机构保证唯一
        }
        resultVo = this.regiest(regiesterInfo);
        LOGGER.info("商户注册返回结果"+resultVo.toString());
        if(resultVo.isSuccess()){
            UcOrgMapping  data = new UcOrgMapping();
            String loginName=regiesterInfo.getUserName();
            String orgCodeVirtual=regiesterInfo.getOrgCode();
            try{
                data.setLoginName(loginName);
                data.setOrgCodeActual(actualOrgCode);
                data.setOrgCodeVirtual(orgCodeVirtual);
                data.setMechantName(mechantName);
                data.setCreatedTime(new Date());
                data.setUpdatedTime(new Date());
                ucOrgMappingMapper.insertSelective(data);  
            }catch(Exception e){
               LOGGER.error("商户注册插入机构映射表失败",e); 
            }
        }
        return resultVo;
    }

}
