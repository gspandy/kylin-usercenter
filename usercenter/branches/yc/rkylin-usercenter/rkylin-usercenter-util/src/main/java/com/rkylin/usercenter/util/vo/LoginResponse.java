package com.rkylin.usercenter.util.vo;

import java.io.Serializable;

public class LoginResponse implements Serializable{

    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    
     private Integer retryTimes;
     private String xAuthToken;
     private String authCode;
     
     
 
    public Integer getRetryTimes() {
        return retryTimes;
    }
    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }
    public String getxAuthToken() {
        return xAuthToken;
    }
    public void setxAuthToken(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }
    public String getAuthCode() {
        return authCode;
    }
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    @Override
    public String toString() {
        return "LoginResponse [retryTimes=" + retryTimes + ", xAuthToken=" + xAuthToken + ", authCode=" + authCode
                + "]";
    }
    public LoginResponse() {
        super();
        // TODO Auto-generated constructor stub
    }
    public LoginResponse(Integer retryTimes, String xAuthToken, String authCode) {
        super();
        this.retryTimes = retryTimes;
        this.xAuthToken = xAuthToken;
        this.authCode = authCode;
    }
 
     
 
 

}
