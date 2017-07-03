package com.rongcapital.usercenter.provider.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class CaasProxyAndRedisExecTimeAspect {
    
    private static Logger logger = LoggerFactory.getLogger("timelogger");

    @Around("execution(* com.rkylin.usercenter.util.CaasUserServiceProxy.*(..)) || execution(* com.rongcapital.usercenter.provider.util.RedisProxy.*(..))")
    public Object aroundMethod(ProceedingJoinPoint pjd){
        
        Object result = null;
        
        String methodName = pjd.getSignature().getName();
        String canonicalName = pjd.getTarget().getClass().getCanonicalName();
        StopWatch stopWatch = new StopWatch(canonicalName+" exec method:"+methodName);
        
        try {
            stopWatch.start();
            
            result = pjd.proceed();
            
            stopWatch.stop();
        } catch (Throwable e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
        
        logger.debug(stopWatch.prettyPrint());
        
        return result;
    }

}
