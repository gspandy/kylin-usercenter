package com.ropapi.response;

import java.io.Serializable;

public class BaseResponse<T extends Serializable> extends Response implements Serializable{
	
	/**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    private String responsecode;
	private String message;
	
	private T data;
	
	
	public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public String getResponsecode() {
		return responsecode;
	}
	public void setResponsecode(String responsecode) {
		this.responsecode = responsecode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
