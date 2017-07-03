package com.rongcapital.usercenter.service.usercenter;

import java.lang.Character.UnicodeScript;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;























import com.rkylin.usercenter.util.CaasUserServiceProxy;
import com.rongcapital.redis.sdr.base.BaseRedisService;
import com.rongcapital.usercenter.api.exception.RegiesterException;
import com.rongcapital.usercenter.api.po.ExtendUserInfo;
import com.rongcapital.usercenter.api.po.UserInfoPo;
import com.rongcapital.usercenter.api.po.UserInfoPo.USERTYPE;
import com.rongcapital.usercenter.api.service.UserCenterService;
import com.rongcapital.usercenter.api.service.UserRegiestAndLoginService;
import com.rongcapital.usercenter.api.vo.Base64VcodeResponse;
import com.rongcapital.usercenter.api.vo.BaseResultVo;
import com.rongcapital.usercenter.api.vo.LoginInfo;
import com.rongcapital.usercenter.api.vo.LoginInfo.TerminalType;
import com.rongcapital.usercenter.api.vo.PersonRegiesterInfo;
import com.rongcapital.usercenter.api.vo.UserRegiesterInfo;
import com.rongcapital.usercenter.api.vo.UserRegiesterInfo.RegiesterType;
import com.rongcapital.usercenter.provider.po.UserInfo;
import com.rongcapital.usercenter.provider.service.LocalUserService;
import com.rongcapital.usercenter.provider.util.UserRedisSessionUtils;
import com.rongcapital.usercenter.provider.vo.RedisAuthInfo;
import com.rongcapital.usercenter.service.base.TestBase;

public class TestService extends TestBase {
    private String app1ConFilePath = "F:/cfca_workspace/agent-sources-1.1.0/caas-sample/conf/my-caas-agent-settings.yaml";
    private String app1ConFilePath1 = "F:/cfca_workspace/agent-sources-1.1.0/caas-sample/conf/my-caas-agent-settings1.yaml";
     
    @Autowired
    private BaseRedisService<String, String> baseRedisService;
  
    @Resource(name = "redisTemplate")
    protected HashOperations<String, String, Object> hashOperations;

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;
    
    
    @Autowired 
    private UserCenterService userCenterService;
    
    @Autowired
    private UserRegiestAndLoginService userRegiestAndLoginService; 
    
    @Autowired
    private CaasUserServiceProxy caasUserServiceProxy; 
    
    @Autowired
    private LocalUserService  localUserService;
   
    @Test
    @Rollback(false)
    public void test(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("helloketty020");
        localUserService.modifyPwd(userInfo, "123455");
    }

    
    @Test
    public  void regist() throws RegiesterException{
        UserInfoPo  po= new UserInfoPo();
        PersonRegiesterInfo mt= new PersonRegiesterInfo();
        po.setUserName("test1113");
        
        po.setUserType(USERTYPE.PERSON);
        po.setPwd("a906449d5769fa7361d7ecc6aa3f6d28");
        mt.setUserName("helloketty098");
      //  mt.setVcode("yymv");
        mt.setPassword("123456789");
        mt.setRegiesterType(RegiesterType.PERSON);
        mt.setUserCname("ketty猫");
        mt.setAppCode("12");
//        BaseResultVo<Boolean> regist = userCenterService.regiest(po);
        BaseResultVo<Boolean> regist = userRegiestAndLoginService.regiest(mt);
        System.out.println(regist.isSuccess());
        
    }
    
    @Test
    public void login(){
        LoginInfo logininfo= new LoginInfo();
        logininfo.setAppCode("12"); 
        logininfo.setTerminalType(TerminalType.ISO);
        logininfo.setLoginName("helloketty098");
        logininfo.setPassword("123456789");
       // logininfo.setxAuthToken("50819d30-4b02-4b14-9d38-e390d65ef8a6");
       // logininfo.setVcode("wlun");
      //  logininfo.setxAuthToken("c5fe853c-d2ab-4328-9f07-860664d58b06");
      //  logininfo.setTerminalNo("");
        BaseResultVo<String> login = userRegiestAndLoginService.login(logininfo);
        System.out.println("=--------------");
        System.out.println(login.getResultData());    
        System.out.println("=--------------");
    }
    
    
    @Test
    public void passwordHandle() {
        UserInfoPo userInfo = new UserInfoPo();
        userInfo.setUserName("helloketty065");
        String   oldPwd="a906449d5769fa7361d7ecc6aa3f6d28"; //123abc
        String   newPwd="dfwerwer1"; //123456
        String   type="1";
        String appCode = "12";
        userCenterService.passwordReset(userInfo, newPwd, appCode);
        
    }

    @Test
    public void passwordUpdate(){
        
        String userToken="ae181682-2046-4159-8482-7a15d709b4cb";
        String   newPwd="123456789"; //123456
        String   oldPwd ="dfwerwer1"; //123abc
        String   appCode="12"; 
        String   vcode="";
        String   xAuthToken="";
        BaseResultVo<String> passwordUpdate = userCenterService.passwordUpdate(userToken, oldPwd, newPwd, appCode, vcode, xAuthToken);
        System.out.println(passwordUpdate);
    }
    
    @Test
    public void getUserInfo() throws Exception {
        String userToken="b3fb50c5-57d6-4bf8-ae37-3b88d16ad723";
        String appCode="15";
        BaseResultVo<ExtendUserInfo> userInfo = userCenterService.getUserInfo(userToken);
        ExtendUserInfo resultData = userInfo.getResultData();
        System.out.println(resultData);
    }

    @Test
    public void checkAuth() {
        String userToken="067ef9c1-578b-421b-993b-f46d4a61b83a";
        String appCode="12";
        BaseResultVo<Boolean> checkAuth = userCenterService.checkAuth(userToken, appCode,TerminalType.ISO);
        System.out.println(checkAuth);
    }

//    @Test
//    public void Auth() {
//        String userToken="c5d041eb-c784-46e6-9dc8-a234458a9bca";
//        String appCode="12";
//        userCenterService.Auth(userToken, appCode);
//    }
    @Test
    public void getAuthcode(){
        String userToken="da39c719-1900-493b-b239-221eb27f4056";
        String appCode="12";
       // userCenterService.getAuthCode(userToken, appCode);
    }
    @Test
    public void logout() {

        String userToken="e886186c-ccb7-4c7b-8b66-d896b01e8bf7";
      //  String userToken="";
        String appCode="12";
        userCenterService.logout(userToken, appCode, TerminalType.ISO);
    }
   
    @Test
    public void check(){
        
        RedisAuthInfo authInfo = UserRedisSessionUtils.getAuthInfo("12","38737bf1-093e-46c6-a94a-041319a507af"); 
        
     //  BaseResultVo<Boolean> checkAuth = caasUserServiceProxy.checkAuth(app1ConFilePath, authInfo.getAccessToken(), "", "12");
       
      // System.out.println(checkAuth.isSuccess());  
    }
    @Test
   public  void getVcode(){
        String appCode="11";
        String xAuthToken="";
        BaseResultVo<Base64VcodeResponse> vo= userRegiestAndLoginService.getBase64Vcode(appCode, xAuthToken);
        System.out.println(vo.isSuccess());
    } 
   
    
  
}
