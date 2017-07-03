package com.rkylin.usercenter.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import cn.rongcapital.caas.agent.AccessTokenInfo;
import cn.rongcapital.caas.agent.CaasAgent;
import cn.rongcapital.caas.agent.CaasAgentSettings;
import cn.rongcapital.caas.agent.ChangePasswordResult;
import cn.rongcapital.caas.agent.LoginResult;
import cn.rongcapital.caas.agent.RegisterResult;
import cn.rongcapital.caas.agent.StateableCaasAgent;
import cn.rongcapital.caas.agent.UserAuthStatus;
import cn.rongcapital.caas.agent.UserInfo;
import cn.rongcapital.caas.agent.ValidateResult;
import cn.rongcapital.caas.agent.VcodeResult;

import com.rkylin.usercenter.util.vo.LoginResponse;
import com.rkylin.usercenter.util.vo.RegiesterResponse;
import com.rongcapital.usercenter.api.po.ExtendUserInfo;
import com.rongcapital.usercenter.api.vo.AuthResultVo;
import com.rongcapital.usercenter.api.vo.Base64VcodeResponse;
import com.rongcapital.usercenter.api.vo.BaseResultVo;

public class CaasAgentFacade {

    private static final Logger logger = LoggerFactory.getLogger(CaasAgentFacade.class);

    private CaasAgent caasAgent;

    private StateableCaasAgent stateableCaasAgent;

    private String appKey;

    public CaasAgentFacade(String confFilePath, String appKey) {

        this.appKey = appKey;
        URL resource = Thread.currentThread().getContextClassLoader().getResource(confFilePath);
        if (null == resource) {
            throw new RuntimeException(confFilePath + "在classpath环境下找不到");
        }
        CaasAgentSettings settings = new CaasAgentSettings();
        Yaml yaml = new Yaml();
        try {
            Map map = (Map) yaml.load(new FileInputStream(resource.getFile()));

            settings.setAppKey(map.get("appKey").toString());
            settings.setAppSecret(map.get("appSecret").toString());
            settings.setCaasApiUrl(map.get("caasApiUrl").toString());
            settings.setProxyEnabled(map.get("proxyEnabled").equals(true) ? true : false);
            settings.setProxyHost(map.get("proxyHost").toString());
            settings.setSignEnabled(false);
            settings.setSslEnabled(map.get("sslEnabled").equals(true) ? true : false);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logger.error("读取配置文件失败",e);
        }

        this.stateableCaasAgent = new StateableCaasAgent();
        this.stateableCaasAgent.setSettings(settings);
        // this.stateableCaasAgent.setSettingsYamlFile(fullPath);
        //
        this.caasAgent = new CaasAgent();
        this.caasAgent.setSettings(settings);
        // this.caasAgent.setSettingsYamlFile(fullPath);

    }

    public void start() {
        this.stateableCaasAgent.start();
        this.caasAgent.start();
    }

    public void stop() {
        this.stateableCaasAgent.stop();
        this.caasAgent.stop();
    }

    public BaseResultVo<RegiesterResponse> regiest(String loginName, String password, String vcode, String xAuthToken) {
        BaseResultVo<RegiesterResponse> resultVo = new BaseResultVo<RegiesterResponse>();
        CaasAgentSettings settings = stateableCaasAgent.getSettings();
        String caasKey = settings.getAppKey();
        RegisterResult register =
                this.stateableCaasAgent.register(loginName, null, null, password, vcode, caasKey, xAuthToken);

        if (register.isSuccess()) {
            xAuthToken = register.getxAuthToken();
            String userCode = register.getUserCode();
            RegiesterResponse regiesterResponse = new RegiesterResponse(userCode, xAuthToken);
            resultVo.setSuccess(true);
            resultVo.setResultData(regiesterResponse);
        } else {
            resultVo.setSuccess(false);
            resultVo.setErrorMsg(register.getErrorMessage());
            resultVo.setResponseCode(register.getErrorCode());
        }
        return resultVo;

    }

    public BaseResultVo<String> isLoginNameExists(String loginName, String xAuthToken) {
        BaseResultVo<String> resultVo = new BaseResultVo<String>();
        ValidateResult validateUserName = this.stateableCaasAgent.validateUserName(loginName, xAuthToken);
        xAuthToken = validateUserName.getxAuthToken();
        resultVo.setResultData(xAuthToken);
        if (validateUserName.isSuccess()) {
            resultVo.setSuccess(false);
        } else {
            resultVo.setSuccess(true);
            resultVo.setErrorMsg(validateUserName.getErrorMessage());
            resultVo.setResponseCode(validateUserName.getErrorCode());
        }
        return resultVo;
    }

    public BaseResultVo<LoginResponse> login(String loginName, String password, String vcode, String xAuthToken) {
        BaseResultVo<LoginResponse> resultVo = new BaseResultVo<LoginResponse>();

        LoginResult login = this.stateableCaasAgent.login(loginName, password, vcode, xAuthToken);

        Integer retryTimes = login.getRetryTimes();
        xAuthToken = login.getxAuthToken();
        String authCode = login.getAuthCode();

        LoginResponse loginResponse = new LoginResponse(retryTimes, xAuthToken, authCode);
        if (login.isSuccess()) {
            resultVo.setSuccess(true);
        } else {
            resultVo.setSuccess(false);
            resultVo.setErrorMsg(login.getErrorMessage());
            resultVo.setResponseCode(login.getErrorCode());
        }
        resultVo.setResultData(loginResponse);
        return resultVo;
    }

    // 重置密码
    public BaseResultVo<Boolean> resetPwd(String userName, String newPwd) {
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        try {
            boolean resultData = caasAgent.userResetPassword(userName, newPwd);
            if (resultData) {
                resultVo.setSuccess(true);
                resultVo.setResultData(resultData);
            } else {
                resultVo.setSuccess(false);
                resultVo.setResponseCode("");
                resultVo.setErrorMsg("reset password  error");
            }
        }

        catch (Exception e) {
            resultVo.setSuccess(false);
            resultVo.setErrorMsg("重置密码失败");
            logger.error("重置密码失败", e);
            throw new RuntimeException("resetPWD error: " + e.getMessage(), e);
        }

        return resultVo;
    }

    // 更新密码
    public BaseResultVo<String> updatePwd(String accessToken, String oldPassword, String password, String vcode,
            String xAuthToke) {
        BaseResultVo<String> resultVo = new BaseResultVo<String>();
        try {
            ChangePasswordResult changePassword =
                    this.stateableCaasAgent.changePassword(accessToken, oldPassword, password, vcode, xAuthToke);
            if (changePassword.isSuccess()) {
                xAuthToke = changePassword.getxAuthToken();
                resultVo.setSuccess(true);
                resultVo.setResultData(xAuthToke);
            } else {

                resultVo.setSuccess(false);
                resultVo.setErrorMsg(changePassword.getErrorMessage());
                resultVo.setResponseCode(changePassword.getErrorCode());
                if ("E9021".equals(changePassword.getErrorCode())) {//
                    resultVo.setErrorMsg("密码修改失败：原始密码不正确");
                    resultVo.setResponseCode(changePassword.getErrorCode());
                }
            }
        } catch (Exception e) {
            logger.error("修改密码失败", e);
            resultVo.setSuccess(false);
            resultVo.setErrorMsg("修改密码失败:" + e.getMessage());
        }
        return resultVo;
    }

    // 获取用户信息
    public BaseResultVo<ExtendUserInfo> getUserInfo(String accessToken) {
        BaseResultVo<ExtendUserInfo> resultVo = new BaseResultVo<ExtendUserInfo>();
        try {
            UserInfo userInfo = caasAgent.getUserInfo(accessToken);
            ExtendUserInfo personInfo = new ExtendUserInfo();

            if (userInfo != null) {

                personInfo.setUserName(userInfo.getUserName());
                personInfo.setEmail(userInfo.getEmail());
                personInfo.setMobilePhone(userInfo.getMobile());

                resultVo.setSuccess(true);
                resultVo.setResultData(personInfo);
            }
        } catch (Exception e) {

            resultVo.setSuccess(false);
            resultVo.setErrorMsg("获取用户信息失败");
            logger.error("获取用户信息失败", e);
            return resultVo;
        }
        return resultVo;

    }

    // 校验权限
    public BaseResultVo<Boolean>
            checkAuth(String accessToken, String resourceCode, String appCode, String operationCode) {

        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        try {
            UserAuthStatus checkAuth = caasAgent.checkAuth(accessToken, resourceCode, operationCode);
            if (checkAuth.isSuccess()) {
                resultVo.setResultData(checkAuth.isTokenRefreshFlag());// 是否即将过期
                resultVo.setSuccess(true);
            } else {
                resultVo.setSuccess(false);
                resultVo.setResultData(false);
                resultVo.setErrorMsg("校验权限失败");
            }
        } catch (Exception e) {
            resultVo.setSuccess(false);
            resultVo.setErrorMsg("校验权限失败:" + e.getMessage());
            logger.error("校验权限失败", e);
            return resultVo;
        }
        return resultVo;
    }

    public BaseResultVo<Boolean> batchCheckAuth(String accessToken, List<String> resourceCode, String appCode) {
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        List<String> resourceCodes = new ArrayList<String>();
        try {
            for (String code : resourceCode) {
                resourceCodes.add(code);
            }
            // 新版本多了个参数 测试
            UserAuthStatus checkAuth = caasAgent.batchCheckAuth(accessToken, resourceCodes, null);
            if (checkAuth.isSuccess()) {
                resultVo.setSuccess(true);
                resultVo.setResultData(true);
            } else {
                resultVo.setSuccess(false);
                resultVo.setResultData(false);
                resultVo.setErrorMsg("校验权限失败");
            }

        }

        catch (Exception e) {
            resultVo.setSuccess(false);
            resultVo.setErrorMsg("校验权限失败");
            logger.error("校验权限失败", e);
        }

        return resultVo;
    }

    public BaseResultVo<AuthResultVo> userAuth(String authCode, String appCode) {

        BaseResultVo<AuthResultVo> resultVo = new BaseResultVo<AuthResultVo>();
        try {
            AccessTokenInfo userAuth = caasAgent.userAuth(authCode);
            AuthResultVo VO = new AuthResultVo();
            if (userAuth != null) {
                VO.setAccessToken(userAuth.getAccessToken());
                VO.setExpiresIn(userAuth.getExpiresIn());
                VO.setRefreshToken(userAuth.getRefreshToken());
                resultVo.setSuccess(true);
                resultVo.setResultData(VO);
            }
        } catch (Exception e) {
            resultVo.setSuccess(false);
            resultVo.setErrorMsg("授权失败");
            logger.error("授权失败", e);
        }

        return resultVo;
    }

    public BaseResultVo<AuthResultVo> refreshToken(String refreshToken) {

        BaseResultVo<AuthResultVo> resultVo = new BaseResultVo<AuthResultVo>();
        try {
            AccessTokenInfo userAuth = caasAgent.refreshToken(refreshToken);
            AuthResultVo VO = new AuthResultVo();
            if (userAuth != null) {
                VO.setAccessToken(userAuth.getAccessToken());
                VO.setExpiresIn(userAuth.getExpiresIn());
                VO.setRefreshToken(userAuth.getRefreshToken());
                resultVo.setSuccess(true);
                resultVo.setResultData(VO);
            }
        } catch (Exception e) {
            resultVo.setSuccess(false);
            resultVo.setErrorMsg("更新token失败");
            logger.error("更新token失败", e);
        }

        return resultVo;
    }

    public BaseResultVo<Boolean> logout(String accessToken) {
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        try {
            boolean userLogout = caasAgent.userLogout(accessToken);
            if (userLogout) {
                resultVo.setSuccess(true);
                resultVo.setResultData(true);
            } else {
                resultVo.setSuccess(false);
                resultVo.setResultData(false);
                resultVo.setErrorMsg("用户退出失败");
            }

        }

        catch (Exception e) {
            resultVo.setSuccess(false);
            resultVo.setErrorMsg("用户退出失败");
            logger.error("用户退出失败", e);
            return resultVo;
        }

        return resultVo;
    }

    public BaseResultVo<String> getAuthCode(String accessToken) {
        BaseResultVo<String> resultVo = new BaseResultVo<String>();
        try {
            String authCode = caasAgent.getAuthCode(accessToken);
            if (!StringUtils.isEmpty(authCode)) {
                resultVo.setSuccess(true);
                resultVo.setResultData(authCode);
            }
        } catch (Exception e) {
            resultVo.setSuccess(false);
            resultVo.setErrorMsg("重新授权获取授权码失败");
            logger.error("重授权获取授权码失败", e);
            return resultVo;
        }
        return resultVo;

    }

    public BaseResultVo<Base64VcodeResponse> getBase64Vcode(String xAuthToken) {
        BaseResultVo<Base64VcodeResponse> resultVo = new BaseResultVo<Base64VcodeResponse>();
        VcodeResult base64Vcode = this.stateableCaasAgent.base64Vcode(xAuthToken);
        if (base64Vcode.isSuccess()) {
            resultVo.setSuccess(true);
            String vcode = base64Vcode.getBase64Image();
            xAuthToken = base64Vcode.getxAuthToken();
            Base64VcodeResponse resultData = new Base64VcodeResponse(vcode, xAuthToken);
            resultVo.setResultData(resultData);

        } else {
            resultVo.setErrorMsg(base64Vcode.getErrorMessage());
            resultVo.setResponseCode(base64Vcode.getErrorMessage());
        }
        return resultVo;

    }

}
