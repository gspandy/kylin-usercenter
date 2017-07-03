package com.rongcapital.usercenter.api.vo;

import java.io.Serializable;

public class RegistResultVo implements Serializable {
    
    private static final long serialVersionUID = 1L;

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

    @Override
    public String toString() {
        return "RegistResultVo [userId=" + userId + ", userCode=" + userCode + "]";
    }
    
    

}
