package com.ropapi.service;

import java.util.Map;

import com.ropapi.response.Response;

public interface ISecurityService {

	public Response verifyRequest(Map<String, String[]> requestParams);
}
