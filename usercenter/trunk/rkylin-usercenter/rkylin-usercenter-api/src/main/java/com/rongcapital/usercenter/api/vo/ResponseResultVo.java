package com.rongcapital.usercenter.api.vo;

import java.io.Serializable;

public class ResponseResultVo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String userToken;

    private String userId ; 
    
    private String userCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }


    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @Override
    public String toString() {
        return "ResponseResultVo [userToken=" + userToken + ", userId=" + userId + ", userCode=" + userCode + "]";
    }
    
    

}
