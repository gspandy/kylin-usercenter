package com.rongcapital.usercenter.service.usercenter;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;



import org.springframework.transaction.annotation.Transactional;

import com.rkylin.usercenter.util.CaasUserServiceProxy;
import com.rongcapital.redis.sdr.base.BaseRedisService;
import com.rongcapital.usercenter.api.exception.RegiesterException;
import com.rongcapital.usercenter.api.po.ExtendUserInfo;
import com.rongcapital.usercenter.api.po.IdentifyInfoVo;
import com.rongcapital.usercenter.api.po.UserInfoDetail;
import com.rongcapital.usercenter.api.po.UserInfoPo;
import com.rongcapital.usercenter.api.po.UserInfoPo.USERTYPE;
import com.rongcapital.usercenter.api.service.UserCenterService;
import com.rongcapital.usercenter.api.service.UserRegiestAndLoginService;
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
import com.rongcapital.usercenter.provider.dao.UcOrgMappingMapper;
import com.rongcapital.usercenter.provider.dao.UcSyetemConfigMapper;
import com.rongcapital.usercenter.provider.dao.UserInfoMapper;
import com.rongcapital.usercenter.provider.po.UcOrgMapping;
import com.rongcapital.usercenter.provider.po.UcOrgMappingExample;
import com.rongcapital.usercenter.provider.po.UcOrgMappingExample.Criteria;
import com.rongcapital.usercenter.provider.po.UcSyetemConfig;
import com.rongcapital.usercenter.provider.po.UserInfo;
import com.rongcapital.usercenter.provider.service.LocalUserService;
import com.rongcapital.usercenter.provider.util.GenertaeIDUtil;
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
    
    @Autowired
    private UcSyetemConfigMapper  ucSyetemConfig;
    @Autowired
    private UcOrgMappingMapper ucOrgMappingMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
   
    @Test
    @Rollback(false)
    public void test(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("helloketty020");
        localUserService.modifyPwd(userInfo, "123455");
    }

    
    @Test
    public  void regist() throws RegiesterException{
     //   PersonRegiesterInfo mt= new PersonRegiesterInfo();
        CompanyRegiesterInfo  mt= new CompanyRegiesterInfo();

      //  mt.setUserId("exsit001");
       // mt.setIdNumber("510105199010017995");
        mt.setUserName("mechant4112");
        
        
        mt.setOrgCode("M111111");
      //  mt.setVcode("yymv");
        mt.setPassword("123456");
    //    mt.setRegiesterType(RegiesterType.PERSON);
        mt.setRegiesterType(RegiesterType.COMPANY);
       // mt.setUserCname("ketty猫");
        mt.setAppCode("12");
        mt.setCompanyName("测试企业");
        mt.setBuslince("1111111113");
//        BaseResultVo<Boolean> regist = userCenterService.regiest(po);
        BaseResultVo<ResponseResultVo> regist = userRegiestAndLoginService.regiest(mt);
        System.out.println(regist.isSuccess());
        
    }
    
    @Test
    public void login(){
        LoginInfo logininfo= new LoginInfo();
        logininfo.setAppCode("12"); 
        logininfo.setTerminalType(TerminalType.ANDROID);
        logininfo.setLoginName("mechant1235");
      //  logininfo.setLoginName("13681396045");
     //  logininfo.setPassword("123456a");
        logininfo.setPassword("123456");
//        logininfo.setOrgCode("M000029");
        logininfo.setOrgCode("M111111");
       // logininfo.setxAuthToken("50819d30-4b02-4b14-9d38-e390d65ef8a6");
       // logininfo.setVcode("wlun");
      //  logininfo.setxAuthToken("c5fe853c-d2ab-4328-9f07-860664d58b06");
      //  logininfo.setTerminalNo("");
         LoginResultVo<ResponseResultVo> login = userRegiestAndLoginService.login(logininfo);
        System.out.println("=--------------");
        System.out.println(login.getResultData());    
        System.out.println("=--------------");
    }
    
    
    @Test
    public void passwordHandle() {
        UserInfoPo userInfo = new UserInfoPo();
        userInfo.setUserName("helloketty098");
        String userLoginName="HELL1";
        String orgCode="M00002911";
        String   newPwd="123456123"; //123456
        String   type="1";
        String appCode = "12";
      // String accessTicket="";
        String accessTicket=userCenterService.getAccessTicket(userLoginName, orgCode, 1234).getResultData();
        BaseResultVo<Boolean> passwordReset = userCenterService.passwordReset(userLoginName, orgCode, accessTicket, newPwd, appCode);
        System.out.println(passwordReset.getResultData());
    }

    @Test
    public void passwordUpdate(){
        
        String userToken="7ec0665f-3a28-4c64-a574-e953fff04cb5";
        String   newPwd="123456789"; //123456
        String   oldPwd ="1234567891"; //123abc
        String   appCode="12"; 
        String   vcode="";
        String   xAuthToken="";
        BaseResultVo<String> passwordUpdate = userCenterService.passwordUpdate(userToken, oldPwd, newPwd, appCode, vcode, xAuthToken);
        System.out.println(passwordUpdate);
    }
    
    @Test
    public void getUserInfo() throws Exception {
       // String userToken="9eebf503-a41a-4424-ad7c-ea98e86ff326";
        String userToken="124b6f0f-deff-4e17-a6f3-076466e99178";
        BaseResultVo<UserInfoDetail> userInfo = userCenterService.getUserInfo(userToken);
        UserInfoDetail resultData = userInfo.getResultData();
        System.out.println(resultData);
    }

    @Test
    public void checkAuth() {
        String userToken="b8fd0c7f-f1c0-40d6-8af3-5de8e7cf51d5";
        String appCode="35";
        String userId="UCB148852024271300001";
        String orgCode="M111111";
        BaseResultVo<Boolean> checkAuth = userCenterService.checkAuth(userToken,userId,orgCode, appCode,true,TerminalType.ANDROID);
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
        String userToken="da39c719-1900-493b-b239-221eb27f40561";
        String appCode="12";
       // userCenterService.getAuthCode(userToken, appCode);
    }
    @Test
    public void logout() {

        String userToken="b8c04aca-3de4-464a-8a10-4e6da4584bcd";
      //  String userToken="";
        String appCode="12";
        BaseResultVo<Boolean> logout = userCenterService.logout(userToken, appCode, TerminalType.ANDROID);
        System.out.println(logout.isSuccess());
    }
   
    @Test
    public void check(){
        
     //   RedisAuthInfo authInfo = UserRedisSessionUtils.getAuthInfo("12","9eebf503-a41a-4424-ad7c-ea98e86ff326"); 
        
       BaseResultVo<Boolean> checkAuth = caasUserServiceProxy.checkAuth("conf/12-caas-agent-settings.yaml","12", "b08fb13a828c471c82525b024865efe01",  "","");
       System.out.println(checkAuth.isSuccess());  
    }
    @Test
   public  void getVcode(){
        String appCode="11";
        String xAuthToken="";
        BaseResultVo<Base64VcodeResponse> vo= userRegiestAndLoginService.getBase64Vcode(appCode, xAuthToken);
        System.out.println(vo.isSuccess());
    } 
   
   @Test
   public void  getLoginErrorTime(){
       String userLoginName="HELL1";
       String orgCode="M00002911";
       BaseResultVo<Integer> errorLoginTime = userCenterService.getErrorLoginTime(userLoginName, orgCode);
       System.out.println(errorLoginTime.getResultData());
   }
  @Test
  public void isIndetify(){
     // String userLoginName="13681396013";
      String userLoginName="helloketty00053";
     /// String orgCode="M000029";
      String orgCode="P0022";
      BaseResultVo<Boolean> identify = userCenterService.isIdentify(userLoginName, orgCode);
      System.out.println(identify);
  }
  @Test
  @Rollback(false)
  public void identify(){
      String userToke="3e74c62a-ecd9-471d-aaee-b651fb68ba04";
    //  String userLoginName="helloketty001";
      String idcardNo="510105199010017998";
      String realName="kettycat111";
     // String 
       IdentifyInfoVo identifyInfoVo = new IdentifyInfoVo();
       identifyInfoVo.setUserToken(userToke);
       identifyInfoVo.setIdcardNo(idcardNo);
       identifyInfoVo.setRealName(realName);
       identifyInfoVo.setEthnic("汉");
       identifyInfoVo.setIdCardNoAddress("中华人民共和国");
       identifyInfoVo.setIdcardNoExpireTime("永久有效");
       identifyInfoVo.setNational("中国");
       
      BaseResultVo<String> identify = userCenterService.identify(identifyInfoVo);
      System.out.println(identify.getResponseCode());
  }
  @Test
  public void  getAccessTicket(){
      
      String userLoginName="HELL1";
      String orgCode="M00002911";
      int expireTime=300;
      BaseResultVo<String> accessTicket = userCenterService.getAccessTicket(userLoginName, orgCode, expireTime);
      System.out.println(accessTicket.getResultData());
      
  }
  @Test
  public void  queryUcDB(){
      
      UcSyetemConfig selectByConfig = ucSyetemConfig.selectByConfig("almond_errorpwd_expire_time");
      System.out.println(selectByConfig.toString());
  }
  @Test
  public void getUUID(){
     // GenertaeIDUtil.generateUserId();
      String generateUserCode = GenertaeIDUtil.generateUserCode(RegiesterType.PERSON);
      String generateUserCode1 = GenertaeIDUtil.generateUserCode(RegiesterType.PERSON);
      String generateUserCode2 = GenertaeIDUtil.generateUserCode(RegiesterType.PERSON);
      System.out.println("-----------------------------------------"+generateUserCode);
      System.out.println("------------------------------------------"+generateUserCode1);
      System.out.println("------------------------------------------"+generateUserCode2);
  }
  @Test
  public void modifyIdentify(){
      String userToke="3e74c62a-ecd9-471d-aaee-b651fb68ba04";
      //  String userLoginName="helloketty001";
        String idcardNo="510105199010017996";
        String realName="kettycat1112";
        
         IdentifyInfoVo identifyInfoVo = new IdentifyInfoVo();
         identifyInfoVo.setUserToken(userToke);
         identifyInfoVo.setIdcardNo(idcardNo);
         identifyInfoVo.setRealName(realName);
      BaseResultVo<Boolean> modifyIdentify = userCenterService.modifyIdentify(identifyInfoVo);
      System.out.println(modifyIdentify.isSuccess());
  }
  @Test
  public void registWechat() throws RegiesterException{
      WeChatRegisterInfo  wechat= new WeChatRegisterInfo();
      wechat.setOpenId("11112");
      wechat.setIdNumber("adfwer");
      wechat.setPublicNumberId("123123");
      wechat.setOrgCode("WEIORG");
      wechat.setWeChatId("wechat");
      BaseResultVo<ResponseResultVo> weChatRegist = userRegiestAndLoginService.weChatRegist(wechat);
      System.out.println(weChatRegist.getResultData().toString());
  }
  @Test
  public void  getUserCode(){
      String userId="UCB148343963397300001";
      String orgCode="M000029";
      BaseResultVo<String> userCodeByUserId = userCenterService.getUserCodeByUserId(userId, orgCode);
      String resultData = userCodeByUserId.getResultData();
      System.out.println(resultData);
      
  }
  @Test
  public void testhahah(){
      String idNumber="510105199010017996";
      String orgCode="M00002911";
      BaseResultVo<Boolean> identifyByIdNumber = userCenterService.isIdentifyByIdNumber(idNumber, orgCode);
      System.out.println(identifyByIdNumber.getResultData());
  }
  
  @Test
  public void getInfo(){
      String userName="prsn1212141111";
      String orgCode="test";
      BaseResultVo<UserInfoPo> userInfoByUserNameAndOrgCode = userCenterService.getUserInfoByUserNameAndOrgCode(userName, orgCode);
      System.out.println(userInfoByUserNameAndOrgCode.getResultData().toString());
      
  }
  @Test
  public void testUcOrgMappingDB(){
      UcOrgMapping  data = new UcOrgMapping();
      data.setLoginName("abc");
      data.setOrgCodeActual("efg");
      data.setOrgCodeVirtual("helloword");
      data.setCreatedTime(new Date());
      data.setUpdatedTime(new Date());
    //  ucOrgMappingMapper.insertSelective(data);
      
      
      UcOrgMappingExample example = new UcOrgMappingExample();
      Criteria createCriteria = example.createCriteria();
      createCriteria.andOrgCodeVirtualEqualTo("M111111");
      createCriteria.andLoginNameEqualTo("18310160503");
      //List<UcOrgMapping> selectByExample = ucOrgMappingMapper.selectByExample(example);
      //System.out.println(selectByExample);
    //  UserInfo selectByLoginNameAndUserType = userInfoMapper.selectByLoginNameAndUserType("mechant411", "M111111", "1");
      System.out.println(111);
  }
  
  @Test
  public void  registMechant() throws RegiesterException{
      PersonRegiesterInfo mt= new PersonRegiesterInfo();
         mt.setUserName("mechant1235");
         
         
         mt.setOrgCode("M1111112");
         mt.setPassword("123456");
         mt.setRegiesterType(RegiesterType.PERSON);
         mt.setAppCode("35");
         BaseResultVo<ResponseResultVo> regist = userRegiestAndLoginService.regiestMerchant(mt, "M111112348","MechantName");  
      System.out.println(regist.toString());
  }
}
