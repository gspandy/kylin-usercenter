package com.rkylin.usercenter.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.rkylin.usercenter.util.vo.LoginResponse;
import com.rkylin.usercenter.util.vo.RegiesterResponse;
import com.rongcapital.usercenter.api.po.ExtendUserInfo;
import com.rongcapital.usercenter.api.vo.AuthResultVo;
import com.rongcapital.usercenter.api.vo.Base64VcodeResponse;
import com.rongcapital.usercenter.api.vo.BaseResultVo;


public class CaasUserServiceProxyImpl implements CaasUserServiceProxy{
   
    private static final Logger logger = LoggerFactory.getLogger(CaasUserServiceProxyImpl.class);

    private CaasAgentFacadeFactory agentFacadeFactory;

    @Override
    public BaseResultVo<LoginResponse> login(String confFilePath,String appKey,String loginName, String password,String vcode,String xAuthToken) {
        // TODO Auto-generated method stub
        StopWatch sw = new StopWatch("CAAS登录login");
        sw.start("方法开始");
        BaseResultVo<LoginResponse> login = agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).login(loginName, password, vcode, xAuthToken);
        sw.stop();
        logger.info(sw.prettyPrint());
        return login;
    }

    @Override
    public BaseResultVo<RegiesterResponse> regiest(String confFilePath,String appKey,String loginName, String password,String vcode,String xAuthToken) {
        StopWatch sw = new StopWatch("CAAS注册regist");
        sw.start("方法开始");
        BaseResultVo<RegiesterResponse> regiest = agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).regiest(loginName, password, vcode, xAuthToken);
        sw.stop();
        logger.info(sw.prettyPrint());
        return regiest;
    }

    @Override
    public BaseResultVo<String> isLoginNameExists(String confFilePath,String appKey,String loginName,String xAuthToken) {
        StopWatch sw = new StopWatch("CAAS判断用户名是否存在isLoginNameExists");
        sw.start("方法开始");
        BaseResultVo<String> loginNameExists = agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).isLoginNameExists(loginName, xAuthToken);
        sw.stop();
        logger.info(sw.prettyPrint());
        return loginNameExists;
    }

    public CaasAgentFacadeFactory getAgentFacadeFactory() {
        return agentFacadeFactory;
    }

    public void setAgentFacadeFactory(CaasAgentFacadeFactory agentFacadeFactory) {
        this.agentFacadeFactory = agentFacadeFactory;
    }

    @Override
    public BaseResultVo<Boolean> resetPwd(String confFilePath,String appKey, String userName, String newPwd) {
        StopWatch sw = new StopWatch("CAAS重置密码resetPwd");
        sw.start("方法开始");
        BaseResultVo<Boolean> resetPwd = this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).resetPwd(userName, newPwd);
        sw.stop();
        logger.info(sw.prettyPrint());
        return resetPwd;
    }

    @Override
    public BaseResultVo<String> updatePwd(String confFilePath,String appKey, String accessToken, String oldPassword, String password,String vcode,String xAuthToken) {
        StopWatch sw = new StopWatch("CAAS修改密码服务");
        sw.start("方法开始");
        BaseResultVo<String> updatePwd = this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).updatePwd(accessToken, oldPassword, password, vcode, xAuthToken);
        sw.stop();
        logger.info(sw.prettyPrint());
        return updatePwd;
    }

    @Override
    public BaseResultVo<Boolean>
            checkAuth(String confFilePath, String appKey,String accessToken, String resourceCode,String operationCode ) {
        StopWatch sw = new StopWatch("CAAS校验checkAuth");
        sw.start("方法开始");
         BaseResultVo<Boolean> checkAuth = this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).checkAuth(accessToken, resourceCode, appKey,operationCode);
         sw.stop();
         logger.info(sw.prettyPrint());
         return checkAuth;
    }

    @Override
    public BaseResultVo<Boolean> batchCheckAuth(String confFilePath,String appKey, String accessToken, List<String> resourceCode) {
        return this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).batchCheckAuth(accessToken, resourceCode, appKey);
    }

    @Override
    public BaseResultVo<AuthResultVo> userAuth(String confFilePath,String appKey, String authCode) {
        StopWatch sw = new StopWatch("CAAS登录授权userAuth");
        sw.start("方法开始");
        BaseResultVo<AuthResultVo> userAuth = this.agentFacadeFactory.getCaasAgentFacade(confFilePath,appKey).userAuth(authCode, appKey);
        sw.stop();
        logger.info(sw.prettyPrint());
        return userAuth;
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
