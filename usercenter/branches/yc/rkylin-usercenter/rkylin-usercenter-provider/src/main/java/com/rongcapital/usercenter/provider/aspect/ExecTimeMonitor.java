package com.rongcapital.usercenter.provider.aspect;


public class ExecTimeMonitor {

    private static  ThreadLocal<StopWatch> stopWatch = new ThreadLocal<StopWatch>();
    
    public static StopWatch getStopWatch(){
        StopWatch get = stopWatch.get();
        if(null == get){
            StopWatch value = new StopWatch();
            stopWatch.set(value );
            return value;
        }
        return get;
    }
}
