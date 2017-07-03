package com.rkylin.usercenter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.caas.agent.CaasAgent;
import cn.rongcapital.caas.api.UserAuthResource;
import cn.rongcapital.caas.vo.ChangePasswordForm;
import cn.rongcapital.caas.vo.ChangePasswordResponse;
import cn.rongcapital.caas.vo.LoginForm;
import cn.rongcapital.caas.vo.LoginResponse;
import cn.rongcapital.caas.vo.RegisterForm;
import cn.rongcapital.caas.vo.RegisterResponse;

import com.rongcapital.usercenter.api.vo.BaseResultVo;

public class CaasLoginAgent {
    
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CaasAgent.class);

    /**
     * the agent settings YAML file
     */
    private String settingsYamlFile;



    /**
     * the CAAS userAuth resource proxy
     */
    private UserAuthResource userAuthResourceProxy;

    /**
     * the initialize flag
     */
    
    private  CaasServiceAgent serviceAgent = null;
    
    
    
    public CaasLoginAgent(){
        serviceAgent = new CaasServiceAgent();
    }
    
    /**
     * to start the agent
     */
    public void start() {
       
       this.userAuthResourceProxy =  serviceAgent.initAgent(UserAuthResource.class, this.settingsYamlFile);
    }

    /**
     * to stop the agent
     */
    public void stop() {
        serviceAgent.releaseAgent();
    }
    
    public BaseResultVo<String> login(String loginName,String password){
     // check
        if (!serviceAgent.getInitialized().get()) {
            LOGGER.error("the CAAS agent is NOT started");
            throw new IllegalStateException("the CAAS agent is NOT started");
        }
        LoginForm loginForm = new LoginForm();
        loginForm.setLoginName(loginName);
        loginForm.setPassword(password);
        LoginResponse loginResponse = this.userAuthResourceProxy.login(loginForm );
       
        BaseResultVo<String> resultVo = new BaseResultVo<String>();
        if(loginResponse.isSuccess()){
            resultVo.setSuccess(true);
            resultVo.setResultData(loginResponse.getAuthCode());
        }else{
            LOGGER.error("getUserInfo failed, errorCode: {}, errorMessage: {}", loginResponse.getErrorCode(),
                    loginResponse.getErrorMessage());
            resultVo.setSuccess(false);
            resultVo.setErrorMsg(loginResponse.getErrorMessage());
            resultVo.setResponseCode(loginResponse.getErrorCode());
        }
        return resultVo;
    }
    public BaseResultVo<String> regiest(String loginName,String password){
     // check
        if (!serviceAgent.getInitialized().get()) {
            LOGGER.error("the CAAS agent is NOT started");
            throw new IllegalStateException("the CAAS agent is NOT started");
        }
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUserName(loginName);
        registerForm.setPassword(password);
        
        BaseResultVo<String> resultVo = new BaseResultVo<String>();

        RegisterResponse registerResponse = this.userAuthResourceProxy.register(registerForm);
        if(registerResponse.isSuccess()){
            resultVo.setSuccess(true);
            resultVo.setResultData("userCode");
        }else{
            LOGGER.error("getUserInfo failed, errorCode: {}, errorMessage: {}", registerResponse.getErrorCode(),
                    registerResponse.getErrorMessage());
            resultVo.setSuccess(false);
            resultVo.setErrorMsg(registerResponse.getErrorMessage());
            resultVo.setResponseCode(registerResponse.getErrorCode());
        }
        return resultVo;
    }

    public BaseResultVo<Boolean>  updatePwd(String authCode, String oldPwd, String newPwd){
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();
        ChangePasswordForm  pwdForm= new ChangePasswordForm();
        pwdForm.setAuthCode(authCode);
        pwdForm.setOldPassword(oldPwd);
        pwdForm.setPassword(newPwd);
        try{
        ChangePasswordResponse changePassword = this.userAuthResourceProxy.changePassword(pwdForm);
        if(changePassword.isSuccess()){
            resultVo.setSuccess(true);
            resultVo.setResultData(true);
        }else{
            LOGGER.error("更新密码失败", changePassword.getErrorCode(),
                    changePassword.getErrorMessage());
            resultVo.setSuccess(false);
            resultVo.setErrorMsg(changePassword.getErrorMessage());
            resultVo.setResponseCode(changePassword.getErrorCode());
        }}
        catch(Exception e){
            LOGGER.error("更新密码失败", e);
            resultVo.setSuccess(false);
        }
        
        return resultVo;
    }
    
    public String getSettingsYamlFile() {
        return settingsYamlFile;
    }

    public void setSettingsYamlFile(String settingsYamlFile) {
        this.settingsYamlFile = settingsYamlFile;
    }
    
    
    
}
