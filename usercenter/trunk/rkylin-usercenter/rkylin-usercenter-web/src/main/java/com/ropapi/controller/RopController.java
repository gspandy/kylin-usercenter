package com.ropapi.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ropapi.constants.Constants;
import com.ropapi.constants.SpringBeanConstants;
import com.ropapi.response.ErrorResponse;
import com.ropapi.response.Response;
import com.ropapi.service.ISecurityService;
import com.ropapi.utils.LogUtils;
import com.ropapi.utils.ModelAndViewUtils;
import com.ropapi.utils.SpringBeanUtils;
import com.ropapi.utils.SpringContextBeanMethodInvokeUtil;





@Controller
public class RopController {
	private static Logger logger = LoggerFactory
			.getLogger(RopController.class);
	
	@Resource(name=SpringBeanConstants.SECURITY_SERVICE)
	private ISecurityService securityService;

   
	@Value("${userCenter.rop.system.error.code}")
	private String systemErrorCode;
    
    @RequestMapping("/ropapi")
    public ModelAndView execute(HttpServletRequest request) {
    	Date startTime = new Date();

		@SuppressWarnings("unchecked")
		Map<String, String[]> requestParams = request.getParameterMap();

		//logger.info("-----------appkey--------------"+requestParams.get(Constants.SYS_PARAM_APP_KEY)[0]);
    	// 1. 验证request参数
    	Response response = securityService.verifyRequest(requestParams);
    	response = null;
        String method = request.getParameter(Constants.SYS_PARAM_METHOD);
        
        String format = request.getParameter(Constants.SYS_PARAM_FORMAT);
    	//method pattern ruixu.appid.beanid.invokemethodname
        // 如果验证通过
        if(null == response){
        	long start = System.currentTimeMillis();
        	try {
        		response = SpringContextBeanMethodInvokeUtil.invokeMethod(method, requestParams);
        	} catch (Exception e) {
        		logger.error("rop controller 调用方法"+method+"失败：",e);
        		response = new ErrorResponse(this.systemErrorCode,"调用"+method+"异常");
        	}
        	long end = System.currentTimeMillis();
        	logger.debug("调用接口"+method+"耗时"+(end-start)+"毫秒");
        }
        // 3. 将API结果映射为相应数据格式并返回
        if(null == response){
            logger.error("调用异常，response为null");
        	response = new ErrorResponse(this.systemErrorCode,"调用异常，请检查参数");
        }
        if(!response.getCallResult()) {
    		method = "error";
    	}

        ModelAndView mav = ModelAndViewUtils.getModelAndView(method, format, response);

        String ip = request.getRemoteAddr();
        
        Date endTime = new Date();

        LogUtils.info(requestParams, startTime, endTime, response, ip);

        return mav;
    }

}
