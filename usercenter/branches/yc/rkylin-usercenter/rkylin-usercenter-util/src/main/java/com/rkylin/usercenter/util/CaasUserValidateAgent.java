package com.rkylin.usercenter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.caas.agent.CaasAgent;
import cn.rongcapital.caas.api.ValidationResource;
import cn.rongcapital.caas.vo.ValidationResult;

import com.rongcapital.usercenter.api.vo.BaseResultVo;

public class CaasUserValidateAgent {
    
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
    private ValidationResource validationResource;

    /**
     * the initialize flag
     */
    
    private  CaasServiceAgent serviceAgent = null;
    
    
    
    public CaasUserValidateAgent(){
        serviceAgent = new CaasServiceAgent();
    }
    
    /**
     * to start the agent
     */
    public void start() {
       
       this.validationResource =  serviceAgent.initAgent(ValidationResource.class, this.settingsYamlFile);
    }

    /**
     * to stop the agent
     */
    public void stop() {
        serviceAgent.releaseAgent();
    }
    
   

    public String getSettingsYamlFile() {
        return settingsYamlFile;
    }

    public void setSettingsYamlFile(String settingsYamlFile) {
        this.settingsYamlFile = settingsYamlFile;
    }
    
    public BaseResultVo<Boolean> isLoginNameIsExists(String userName){
     // check
        if (!serviceAgent.getInitialized().get()) {
            LOGGER.error("the CAAS agent is NOT started");
            throw new IllegalStateException("the CAAS agent is NOT started");
        }
        BaseResultVo<Boolean> resultVo = new BaseResultVo<Boolean>();

        ValidationResult validateUserName = this.validationResource.validateUserName(userName);
        resultVo.setSuccess(true);
        if(validateUserName.isSuccess()){
            resultVo.setResultData(false);
        }else {
            LOGGER.info("userResetPassword failed, errorCode: {}, errorMessage: {}",
                    validateUserName.getErrorCode(), validateUserName.getErrorMessage());
            resultVo.setResultData(true);
        }
        return resultVo;
    }
    
}
