package com.ropapi.service;

import com.ropapi.response.ErrorResponse;

public interface IErrorResponseService {
	
	public ErrorResponse getErrorResponse(String code);

	public ErrorResponse getErrorResponse(String code, String msg);

	public ErrorResponse getErrorResponse(String code, String msg, String subCode, String subMsg);

}
