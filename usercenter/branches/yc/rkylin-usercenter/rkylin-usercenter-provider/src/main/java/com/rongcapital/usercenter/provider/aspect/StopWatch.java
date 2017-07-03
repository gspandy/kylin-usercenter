package com.rongcapital.usercenter.provider.aspect;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StopWatch {

    private ConcurrentSkipListMap<Integer,String> starts = new ConcurrentSkipListMap<Integer,String>();
    private AtomicInteger startIndex = new AtomicInteger(1);
    private String stopWatchInfo = "服务调用时间：";
    private final static String SEPRET = "_";
    
    
    
    private Map<Integer,TraceInfo> infos = new ConcurrentSkipListMap<Integer,TraceInfo>();
    
    
    
    
    public void start(String info){
        long currentTimeMillis = System.currentTimeMillis();
        String value = info + SEPRET +currentTimeMillis;
        starts.put(startIndex.getAndIncrement(), value );
    }
    
    public void stop(){
        
        long currentTimeMillis = System.currentTimeMillis();
        Entry<Integer, String> lastEntry = starts.lastEntry();
        
        
        Integer startIndex = lastEntry.getKey();
        starts.remove(startIndex);
        String next = lastEntry.getValue();
      
        String[] split = next.split(SEPRET);
        String info = split[0];
        Long start = Long.valueOf(split[1]);
        
        long second = currentTimeMillis-start;
        
        
        
        TraceInfo traceInfo = new TraceInfo(info, second);;
        infos.put(startIndex, traceInfo);
    }
    
    
    
    
    public StopWatch() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public StopWatch(String stopWatchInfo) {
        this.stopWatchInfo = stopWatchInfo;
    }

    public String prettyPrint(){
        
        if(starts.size() != 0){
            return "StopWatch使用错误";
        }

        StringBuilder sb = new StringBuilder(stopWatchInfo);
        Collection<TraceInfo> values = infos.values();
        for (TraceInfo traceInfo : values) {
                sb.append("\r\n");
                sb.append(traceInfo.toString());
        }
        sb.append("\r\n");
        sb.append("************  level:"+startIndex.get());
        return sb.toString();
    }
    
    
    static class TraceInfo implements Serializable{
        /**
         * Description:
         */
        private static final long serialVersionUID = 1L;
        private String info;
        private Long second;
        public String getInfo() {
            return info;
        }
        public void setInfo(String info) {
            this.info = info;
        }
        public Long getSecond() {
            return second;
        }
        public void setSecond(Long second) {
            this.second = second;
        }
        @Override
        public String toString() {
            return "TraceInfo [info=" + info + ", second=" + second + "]";
        }
        public TraceInfo() {
            super();
            // TODO Auto-generated constructor stub
        }
        public TraceInfo(String info, Long second) {
            super();
            this.info = info;
            this.second = second;
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        final Logger logger = LoggerFactory.getLogger("timelogger");
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
        for(int i = 0 ; i < 100 ; i++)
        newFixedThreadPool.submit(new Runnable() {
            
            @Override
            public void run() {
                
                try{
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start("step 1");
//                    TimeUnit.SECONDS.sleep(2);
                    stopWatch.stop();
                    
                    stopWatch.start("step 2");
                    TimeUnit.SECONDS.sleep(3);
                    stopWatch.stop();
                    
                    
                    stopWatch.start("step 3");
                    TimeUnit.SECONDS.sleep(1);
                    stopWatch.stop();
                    
                    logger.debug(stopWatch.prettyPrint());
                }catch(Exception e){
                    e.printStackTrace();
                }
               
                
            }
        });
        newFixedThreadPool.awaitTermination(1, TimeUnit.HOURS);
        
    }
}
