package com.ropapi.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Rop.api.ApiException;
import com.Rop.api.RopClient;
import com.Rop.api.RopRequest;
import com.Rop.api.RopResponse;
import com.Rop.api.request.ExternalSessionGetRequest;
import com.Rop.api.response.ExternalSessionGetResponse;

public class ROPClientUtils {

    private static Logger logger = LoggerFactory.getLogger(ROPClientUtils.class);
	
	private  static String sessionGet(RopClient ropClien) {
		 
        String sessionKey = null;
        try {
            ExternalSessionGetRequest sessionGetReq = new ExternalSessionGetRequest();
            ExternalSessionGetResponse sessionGetRsp = ropClien.execute(sessionGetReq);
            sessionKey = sessionGetRsp.getSession();
        } catch (Exception ex) {
            logger.error("获取sessionKey失败:",ex);
        } finally {

        }
        return sessionKey;
   }
	public static <T> T execute(RopClient ropClient,RopRequest request) throws ApiException{
		try {
		    long start = System.currentTimeMillis();
			RopResponse execute = ropClient.execute(request, sessionGet(ropClient));
			long end = System.currentTimeMillis();
			logger.debug(request.getApiMethodName()+"接口执行毫秒数:"+(end-start));
			return (T)execute;
		} catch (ApiException e) {
		    logger.error("调用rop方"+request.getApiMethodName()+"法失败:",e);
		    throw e;
		}
	}
}

