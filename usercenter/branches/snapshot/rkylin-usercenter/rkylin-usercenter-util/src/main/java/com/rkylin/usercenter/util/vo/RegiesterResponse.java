package com.rkylin.usercenter.util.vo;

import java.io.Serializable;

public class RegiesterResponse implements Serializable {
    
    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    private String userCode;
    private String xAuthToken;
    
    
    
    
    public RegiesterResponse() {
        super();
        // TODO Auto-generated constructor stub
    }
    public RegiesterResponse(String userCode, String xAuthToken) {
        super();
        this.userCode = userCode;
        this.xAuthToken = xAuthToken;
    }
    public String getUserCode() {
        return userCode;
    }
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getxAuthToken() {
        return xAuthToken;
    }
    public void setxAuthToken(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }
    @Override
    public String toString() {
        return "RegiesterResponse [userCode=" + userCode + ", xAuthToken=" + xAuthToken + "]";
    }
    
    
}
