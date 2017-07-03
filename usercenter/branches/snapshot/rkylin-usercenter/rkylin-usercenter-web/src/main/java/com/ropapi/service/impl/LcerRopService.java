package com.ropapi.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ropapi.response.ErrorResponse;
import com.ropapi.response.LcerTestResponse;
import com.ropapi.response.Response;

@Service("lcerservice")
public class LcerRopService{

	public Response queryM(Map<String, String[]> paramMap) {
		String[] strings = paramMap.get("pm");
		ErrorResponse errorResponse=new ErrorResponse();
		if(null == strings || strings.length == 0){
			errorResponse.setCode("P1");
			errorResponse.setMsg("pm参数不能为空");
			return errorResponse;
		}
		LcerTestResponse response = new LcerTestResponse();
		response.setCallResult(false);
		List<String> list = response.getList();
		list.add("abc");
		list.add("123");
		response.setList(list);
		response.setContent("content:中国");
		return response;
	}

}
