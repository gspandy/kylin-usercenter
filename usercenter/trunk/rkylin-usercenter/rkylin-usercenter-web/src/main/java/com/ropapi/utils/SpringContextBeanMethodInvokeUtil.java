package com.ropapi.utils;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import com.rongcapital.usercenter.util.JsonUtil;
import com.ropapi.response.Response;

@Component
public class SpringContextBeanMethodInvokeUtil implements BeanFactoryAware {

    private static Logger logger = LoggerFactory.getLogger(SpringContextBeanMethodInvokeUtil.class);
    
    private static ConcurrentHashMap<String, SoftReference<Method>> methos =
            new ConcurrentHashMap<String, SoftReference<Method>>();

    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

        this.beanFactory = beanFactory;
    }

    /*
     * urlPattern ruixue.wheatfield.appnName.beanid.invokeMethodName
     */
    public static Response invokeMethod(String urlPattern, Map<String, String[]> requestParams) throws Exception {
        logger.info("invokeMethod入参urlPattern:{}",urlPattern);
        if(logger.isDebugEnabled()){
            if(null != requestParams){
                Set<String> keySet = requestParams.keySet();
                logger.debug("参数--------------------");
                if(null != keySet){
                    for(String key : keySet){
                        logger.debug("参数："+key+" 的值："+Arrays.toString(requestParams.get(key)));
                    }
                }
                logger.debug("参数--------------------");
            }
        }
        if (StringUtils.isNotBlank(urlPattern)) {
            String[] strs = urlPattern.split("\\.");
            if (5 != strs.length) {
                logger.error("rop配置参数必须为ruixue.wheatfield.appnName.beanid.invokeMethodName");
                throw new IllegalArgumentException("rop配置参数必须为ruixue.wheatfield.appnName.beanid.invokeMethodName");
            } else {
                String beanId = strs[3];
                String methodName = strs[4];

                Method method = null;
                // 缓存中取得方法
                if (methos.contains(urlPattern)) {
                    SoftReference<Method> softReferenceMethod = methos.get(beanId);
                    method = softReferenceMethod.get();
                }
                Object bean = beanFactory.getBean(beanId);
                if (null == bean) {
                    String msg = "根据beanId:" + beanId + "没有找到匹配的bean";
                    logger.error(msg);
                    throw new IllegalArgumentException(msg);
                }
                if (null == method) {
                    method = getMethod(bean, methodName);
                    if (null == method) {
                        String msg = "调用的方法不存在";
                        logger.error(msg);
                        throw new IllegalArgumentException(msg);
                    }
                    methos.put(urlPattern, new SoftReference<Method>(method));
                }
                try{
                    Response invokeServiceMethod = invokeServiceMethod(bean, method, requestParams);
                    return invokeServiceMethod;
                }catch(Exception e){
                    logger.error(e.getMessage());
                    throw e;
                }
            }
        } else {
            String msg = "urlPattern不能为空";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

    }

    private static Response invokeServiceMethod(Object bean, Method method, Map<String, String[]> requestParams)
            throws Exception {
        logger.info("invokeServiceMethod入参requestParams:{}",JsonUtil.bean2JsonStr(requestParams));
        if (null == requestParams || requestParams.size() <= 0) {
            logger.debug("调用类："+bean.getClass()+"\t的方法无参方法："+method.getName());
            return (Response) method.invoke(bean, null);
        } else {
            logger.debug("调用类："+bean.getClass()+"\t的方法有参方法："+method.getName());
            return (Response) method.invoke(bean, requestParams);
        }

    }

    private static Method getMethod(Object bean, String methodName) {
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        if (null != declaredMethods && declaredMethods.length > 0) {
            for (Method method : declaredMethods) {
                String getMethodName = method.getName();
                if (methodName.equalsIgnoreCase(getMethodName)) {
                    return method;
                }
            }
        }
        return null;
    }
}
