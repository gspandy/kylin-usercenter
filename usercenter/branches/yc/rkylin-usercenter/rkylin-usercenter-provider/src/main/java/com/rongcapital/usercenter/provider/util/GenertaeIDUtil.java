package com.rongcapital.usercenter.provider.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;




import org.springframework.stereotype.Component;

import com.rongcapital.redis.sdr.base.BaseRedisService;
import com.rongcapital.usercenter.api.vo.UserRegiesterInfo.RegiesterType;

@Component
public class GenertaeIDUtil {

    private static  RedisTemplate<String,Object> redisTemplate;
    
    private static BaseRedisService<String, Object> baseRedisService;
    
    @Autowired
    public  void setRedisTemplate(RedisTemplate redisTemplate) {
        GenertaeIDUtil.redisTemplate = redisTemplate;
    }
    
    public static String generateUserId(RegiesterType regiesterType){
        String ACCOUNT_KEY_PREFIX = "";
        if(RegiesterType.PERSON.equals(regiesterType)){
            ACCOUNT_KEY_PREFIX="UCP";
        }
        if(RegiesterType.COMPANY.equals(regiesterType)){
            ACCOUNT_KEY_PREFIX="UCB";
        }
        if(RegiesterType.WECHAT.equals(regiesterType)){
            ACCOUNT_KEY_PREFIX="UCW";
        }
        String BATCH_FORMAT = "00000";
        long minuteStr = System.currentTimeMillis();
        Long number = redisTemplate.opsForValue().increment(ACCOUNT_KEY_PREFIX + minuteStr, 1l);
        redisTemplate.expire(ACCOUNT_KEY_PREFIX + minuteStr, 1, TimeUnit.MINUTES);
        return ACCOUNT_KEY_PREFIX + minuteStr + RkylinUtil.formatDecimal(number,BATCH_FORMAT);
    }
    /**
     * 
     * Discription:    
     *  户口号：ACCOUNT_CODE
                   长度：20
                   格式：RP  A  1482809873761    0001
                               前3位
                            RP ：个人户口号（person）
                            RC ：企业户口号（company）
                            RI  ：内部户口号（internal）
        
                            A   ：接口调用系统自动开户（auto）
                            M  ：人工开户，数据库sql语句预置（manual）
                            B   ：来自用户中心接口调用系统自动开户（b）
                              中间13位
                                             时间戳 System.currentTimeMillis()
                              后4位
                                             顺序号
     * @param regiesterType
     * @return
     *  String
     * @author bihf
     * @since 2016年12月27日
     */
    public static String generateUserCode(RegiesterType regiesterType){
        
        String ACCOUNT_KEY_PREFIX="";
        if(RegiesterType.PERSON.equals(regiesterType)){
            ACCOUNT_KEY_PREFIX="RPB";
        }
        if(RegiesterType.COMPANY.equals(regiesterType)){
            ACCOUNT_KEY_PREFIX="RCB";
        }
        if(RegiesterType.WECHAT.equals(regiesterType)){
            ACCOUNT_KEY_PREFIX="RCW";
        }
        String BATCH_FORMAT = "0000";
        long minuteStr = System.currentTimeMillis();
        Long number = redisTemplate.opsForValue().increment(ACCOUNT_KEY_PREFIX + minuteStr, 1l);
        redisTemplate.expire(ACCOUNT_KEY_PREFIX + minuteStr, 1, TimeUnit.MINUTES);
        String str=RkylinUtil.formatDecimal(number,BATCH_FORMAT);
        return ACCOUNT_KEY_PREFIX + minuteStr + str;
    }  
    
    public static void main(String[] args) {
        
       System.out.println(System.currentTimeMillis());
       System.out.println(Calendar.getInstance().getTimeInMillis());
       System.out.println(new Date().getTime());
       String res;
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
       long lt = new Long(System.currentTimeMillis());
       Date date = new Date(lt);
       res = simpleDateFormat.format(date);
       System.out.println("res--------"+res);
       
    }
}
