package com.rongcapital.usercenter.provider.util;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class CaasConfigPathUtils {
    
    private static Logger logger = LoggerFactory.getLogger(CaasConfigPathUtils.class);
    
    private static Map<String,String> appCode2path = new ConcurrentHashMap<String, String>();
    
    private static final String appCodePrefix = "caas.appCode.";
    
    public static String  getConfigfile(String appCode){
        if(StringUtils.isNotEmpty(appCode)){
            if(appCode2path.containsKey(appCode)){
                return appCode2path.get(appCode);
            }else{
                ResourceBundle bundle = ResourceBundle.getBundle("caasAppCode2path");
                try{
                    String path = bundle.getString(appCodePrefix+appCode);
                    if(StringUtils.isNotEmpty(path)){
                        appCode2path.put(appCode, path);
                        return path;
                    }
                }catch(Exception e){
                    logger.error("从配置文件获取appCode:{}对应的path异常",appCode,e);
                     throw new RuntimeException("不支持appCode:"+appCode+"的sso服务");
                }
            }
            
        }else{
            throw new RuntimeException("appCode不能为空");
        }
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println(getConfigfile("12"));
    }
}
