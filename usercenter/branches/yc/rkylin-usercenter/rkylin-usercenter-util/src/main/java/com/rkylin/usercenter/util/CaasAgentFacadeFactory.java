package com.rkylin.usercenter.util;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

public class CaasAgentFacadeFactory {
    
    private  Map<String,CaasAgentFacade> map = new ConcurrentHashMap<String,CaasAgentFacade>();
    
     public   CaasAgentFacade getCaasAgentFacade(String confFilePath,String appKey){
         if(StringUtils.isNotEmpty(confFilePath)){
             if(map.containsKey(confFilePath)){
                 return map.get(confFilePath);
             }else{
                 CaasAgentFacade agentFacade = new CaasAgentFacade(confFilePath,appKey);
                 agentFacade.start();
                 map.put(confFilePath, agentFacade);
                 return agentFacade;
             }
             
         }else{
             return null;
         }
     }
     
     public void stop(){
         Collection<CaasAgentFacade> values = map.values();
         for (CaasAgentFacade caasAgentFacade : values) {
             caasAgentFacade.stop();
        }
     }
     
     
}
