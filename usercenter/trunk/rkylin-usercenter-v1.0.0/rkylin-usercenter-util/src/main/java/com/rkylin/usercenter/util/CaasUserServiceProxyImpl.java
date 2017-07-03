package com.rkylin.usercenter.util;

import java.util.List;

import com.rkylin.usercenter.util.vo.LoginResponse;
import com.rkylin.usercenter.util.vo.RegiesterResponse;
import com.rongcapital.usercenter.api.po.ExtendUserInfo;
import com.rongcapital.usercenter.api.vo.AuthResultVo;
import com.rongcapital.usercenter.api.vo.Base64VcodeResponse;
import com.rongcapital.usercenter.api.vo.BaseResultVo;


public class CaasUserServiceProxyImpl implements CaasUserServiceProxy{

    private CaasAgentFacadeFactory agentFacadeFactory;

    @Override
    public BaseResultVo<LoginResponse> login(String confFilePath,String appKey,String loginName, String password,String vcode,String xAuthToken) {
        // TODO Auto-generated method stub
        return agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).login(loginName, password, vcode, xAuthToken);
    }

    @Override
    public BaseResultVo<RegiesterResponse> regiest(String confFilePath,String appKey,String loginName, String password,String vcode,String xAuthToken) {
        // TODO Auto-generated method stub
        return agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).regiest(loginName, password, vcode, xAuthToken);
    }

    @Override
    public BaseResultVo<String> isLoginNameExists(String confFilePath,String appKey,String loginName,String xAuthToken) {
        // TODO Auto-generated method stub
        return agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).isLoginNameExists(loginName, xAuthToken);
    }

    public CaasAgentFacadeFactory getAgentFacadeFactory() {
        return agentFacadeFactory;
    }

    public void setAgentFacadeFactory(CaasAgentFacadeFactory agentFacadeFactory) {
        this.agentFacadeFactory = agentFacadeFactory;
    }

    @Override
    public BaseResultVo<Boolean> resetPwd(String confFilePath,String appKey, String userName, String newPwd) {
        return this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).resetPwd(userName, newPwd);
    }

    @Override
    public BaseResultVo<String> updatePwd(String confFilePath,String appKey, String accessToken, String oldPassword, String password,String vcode,String xAuthToken) {
        return this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).updatePwd(accessToken, oldPassword, password, vcode, xAuthToken);
    }

    @Override
    public BaseResultVo<Boolean>
            checkAuth(String confFilePath, String appKey,String accessToken, String resourceCode,String operationCode ) {
        return this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).checkAuth(accessToken, resourceCode, appKey,operationCode);
    }

    @Override
    public BaseResultVo<Boolean> batchCheckAuth(String confFilePath,String appKey, String accessToken, List<String> resourceCode) {
        return this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).batchCheckAuth(accessToken, resourceCode, appKey);
    }

    @Override
    public BaseResultVo<AuthResultVo> userAuth(String confFilePath,String appKey, String authCode) {
        return this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).userAuth(authCode, appKey);
    }

    @Override
    public BaseResultVo<AuthResultVo> refreshToken(String confFilePath,String appKey, String refreshToken) {
        return this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).refreshToken(refreshToken);
    }

    @Override
    public BaseResultVo<Boolean> logout(String confFilePath,String appKey, String accessToken) {
        return this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).logout(accessToken);
    }

    @Override
    public BaseResultVo<ExtendUserInfo> getUserInfo(String confFilePath,String appKey, String accessToken) {
        return this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).getUserInfo(accessToken);
    }

    @Override
    public BaseResultVo<String> getAuthCode(String confFilePath,String appKey,String accessToken) {
        return this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).getAuthCode(accessToken);
    }

    @Override
    public BaseResultVo<Base64VcodeResponse> getBase64Vcode(String confFilePath, String appKey, String xAuthToken) {
        // TODO Auto-generated method stub
        return this.agentFacadeFactory.getCaasAgentFacade(confFilePath, appKey).getBase64Vcode(xAuthToken);
    } 
    
  

    
    
    
    
    
}
