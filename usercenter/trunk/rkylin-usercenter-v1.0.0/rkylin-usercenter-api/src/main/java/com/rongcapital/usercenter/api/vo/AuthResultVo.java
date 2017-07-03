package com.rongcapital.usercenter.api.vo;

import java.io.Serializable;

public class AuthResultVo implements Serializable{
    
    /**
     * Description:
     */
    private static final long serialVersionUID = -1928874033537212970L;
    /**
     * 授权token
     */
    private  String accessToken;
    /**
     * 过期时间
     */
    private  Integer expiresIn;
    /**
     * 更新token
     */
    private  String refreshToken;
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public Integer getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    @Override
    public String toString() {
        return "AuthResultVo [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", refreshToken="
                + refreshToken + "]";
    }
    
    
    
}
