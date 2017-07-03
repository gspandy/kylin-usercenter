package com.rongcapital.usercenter.service.usercenter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;


















import cn.rongcapital.caas.po.User;

import com.rongcapital.redis.sdr.base.BaseRedisService;
import com.rongcapital.usercenter.api.vo.LoginInfo.TerminalType;
import com.rongcapital.usercenter.provider.po.UserInfo;
import com.rongcapital.usercenter.provider.util.UserRedisSessionUtils;
import com.rongcapital.usercenter.provider.vo.RedisAuthInfo;
import com.rongcapital.usercenter.service.base.TestBase;

public class TestRedis extends TestBase{
    
    @Autowired
    private BaseRedisService<String, Object> baseRedisService;
 
    
    @Resource(name = "redisTemplate")
    protected HashOperations<String, String, Object> hashOperations;
    
    @Autowired
    private RedisTemplate<String, Long> redisTemplate;
    

   
    @Test
    @Rollback(false)
    public void saveTest() {
        try {
            
            
            
            baseRedisService.set("testmq11111", "mqvalue", 10000);
            UserInfo suse = (UserInfo)baseRedisService.get("sso_login_f18aed1e-96e6-4564-8e5d-28a3d4457c54");
            System.out.println(baseRedisService.hasKey("testmq1111221"));
            Map<String, String> map1 = new HashMap<String, String>();
            Map<String, String> map2 = new HashMap<String, String>();
            Map<String, String> map3 = new HashMap<String, String>();
            Map<String, String> map4 = new HashMap<String, String>();
            map1.put("messageName", "rkylin_mtkernel_queue_001");
            map2.put("messageName", "rkylin_mtkernel_queue_002");
            map3.put("messageName", "rkylin_mtkernel_queue_001");
            map4.put("messageName", "rkylin_mtkernel_queue_003");
            map1.put("persistence", "1");
            map2.put("persistence", "1");
            map3.put("persistence", "1");
            map4.put("persistence", "1");
            map1.put("transacted", "0");
            map2.put("transacted", "0");
            map3.put("transacted", "0");
            map4.put("transacted", "0");
            
            
//            baseRedisService.bathSet(map, 10000);
          //  baseRedisService.set("testmap", map,10000);
            hashOperations.putAll("mqmtaegis00123", map1);
            hashOperations.putAll("mqmtaegis00234", map2);
            hashOperations.putAll("mqsettle001444", map3);
            hashOperations.putAll("MOO1framework00355", map4);
            String[] keys = { "aaa", "bbb", "ccc", "ddd" };
         //   List<String> list = baseRedisService.bathGet(keys);
         
        //   String a = hashOperations.get("testmap", "aaa").toString();
        //   System.out.println(a);
          

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
   
    
    @Test
    public void sdf() throws Exception{
        boolean set = baseRedisService.set("ccdccc", "mqvalue", 10);
        Set<String> keys = redisTemplate.keys("sso*");
        String redisUserLoginKey = "sso_login_aaaaaaaa";
        String redisUserSessionKey ="sso_login_aaaaaaaa_1";
      
        RedisAuthInfo oldAuthInfo = new RedisAuthInfo();
        oldAuthInfo.setAccessToken("aaaaaaaaaaaa");
        oldAuthInfo.setExpiresIn(360000);
        oldAuthInfo.setRefreshToken("bbbbbbbbbbbb");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("1111");
       // userInfo.setLoginName("hellowker");
        
      //  baseRedisService.set(redisUserSessionKey,baseRedisService.getValueSerializer().serialize(userInfo), 360000);
       // baseRedisService.set(redisUserSessionKey,baseRedisService.getValueSerializer().serialize(userInfo) , maxExpires);
        redisTemplate.boundHashOps(redisUserLoginKey).put("", oldAuthInfo);
        redisTemplate.boundHashOps(redisUserLoginKey).expire(360000, TimeUnit.SECONDS);
        redisTemplate.keys("sso*");
        System.out.println(redisTemplate.boundHashOps(redisUserLoginKey).get(""));
        System.out.println(redisTemplate.boundHashOps("sso_login_c5d041eb-c784-46e6-9dc8-a234458a9bca_0").get("12"));
    }

    
    
    @Test 
    public void testRedis() throws Exception{
        String userToken="f060390b-732b-47a7-aa17-04c242159ac9";
        String appcode="12";
        TerminalType terminalType =TerminalType.PC;
        String redisUserSessionKey = UserRedisSessionUtils.getRedisUserSessionKey(userToken);
        UserInfo userSession = UserRedisSessionUtils.getUserSession(userToken);
        RedisAuthInfo authInfoWithTerminalType = UserRedisSessionUtils.getAuthInfoWithTerminalType(appcode, userToken, terminalType);
        redisTemplate.boundHashOps("sso_login_f060390b-732b-47a7-aa17-04c242159ac9_3").put("14", authInfoWithTerminalType);;
      //  redisTemplate.boundHashOps("sso_login_f060390b-732b-47a7-aa17-04c242159ac9_3").delete("12");
        System.out.println(111);
    }
    
    @Test 
    public void likeQuery(){
        Set<String> keys = redisTemplate.keys("sso_login_b26819c5-03cd-455f-a168-c3db74cbba2d*");
        Set<String> keys1 = redisTemplate.keys("sso*");
        System.out.println(11);
        for(String key:keys){
            System.out.println("------------"+key);
        }
    }
    @Test
    public  void  getXauthCode() throws Exception{
        //String userToken="7d51d2dc-667f-4a82-9a99-843a22cd45c8";
    //    String xAuthToken = UserRedisSessionUtils.getXAuthToken(userToken);
      //  System.out.println(xAuthToken);
      
    }
    
    @Test
    public  void testRedisExpireTime() throws Exception{
        
        baseRedisService.set("testmq111111111111111111", "mqvalue", 10000);
        
    }
    @Test
    public  void  test111() throws Exception{
       // UserRedisSessionUtils.updateUserSession("testmq111111111111111111","testvlue");
        String object = baseRedisService.get("sso_token_15110591792_M111111").toString();
        System.out.println(object);
    }
}
