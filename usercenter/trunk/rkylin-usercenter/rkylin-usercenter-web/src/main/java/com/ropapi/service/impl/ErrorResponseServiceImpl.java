package com.ropapi.service.impl;

import java.util.Properties;

import com.ropapi.response.ErrorResponse;
import com.ropapi.service.IErrorResponseService;

public class ErrorResponseServiceImpl implements IErrorResponseService {

	private Properties errorCodeProperties;

	public Properties getErrorCodeProperties() {
		return errorCodeProperties;
	}

	public void setErrorCodeProperties(Properties errorCodeProperties) {
		this.errorCodeProperties = errorCodeProperties;
	}

	@Override
	public ErrorResponse getErrorResponse(String code) {
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setCallResult(false);

		errorResponse.setCode(code);
		errorResponse.setMsg(errorCodeProperties.getProperty(code));

		return errorResponse;
	}

	@Override
	public ErrorResponse getErrorResponse(String code, String msg) {
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setCallResult(false);
		
		errorResponse.setCode(code);
		errorResponse.setMsg(msg);
		
		return errorResponse;
	}

	@Override
	public ErrorResponse getErrorResponse(String code, String msg,
			String subCode, String subMsg) {
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setCallResult(false);

		errorResponse.setCode(code);
		errorResponse.setMsg(msg);
		errorResponse.setSubCode(subCode);
		errorResponse.setSubMsg(subMsg);

		return errorResponse;
	}

}
