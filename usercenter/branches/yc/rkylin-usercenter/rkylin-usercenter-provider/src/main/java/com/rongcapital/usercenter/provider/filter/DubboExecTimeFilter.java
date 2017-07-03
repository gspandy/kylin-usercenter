package com.rongcapital.usercenter.provider.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

@Component
public class DubboExecTimeFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger("timelogger");
   



    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        
        String methodName = invocation.getMethodName();
        String interfaceName = invocation.getInvoker().getInterface().getName();
        StopWatch stopWatch = new StopWatch(interfaceName+" exec method:"+methodName);
        stopWatch.start("dubbo服务执行时间");
        Result result = invoker.invoke(invocation);
        stopWatch.stop();
        logger.debug(stopWatch.prettyPrint());
        return result;
        
        
    }

}
