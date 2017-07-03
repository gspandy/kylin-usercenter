package com.rongcapital.usercenter.provider.vo;

import java.io.Serializable;

import com.rongcapital.usercenter.api.vo.AuthResultVo;

public class RedisAuthInfo implements Serializable{

    
    /**
     * Description:
     */
    private static final long serialVersionUID = -1928874033537212970L;
    /**
     * 授权token
     */
    public  String accessToken;
    /**
     * 过期时间
     */
    public  Integer expiresIn;
    /**
     * 更新token
     */
    
    public  String refreshToken;
    

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
        return "RedisAuthInfo [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", refreshToken="
                + refreshToken + "]";
    }

    public  RedisAuthInfo valueOf(AuthResultVo authResultVo){
        this.accessToken = authResultVo.getAccessToken();
        this.expiresIn = authResultVo.getExpiresIn();
        this.refreshToken = authResultVo.getRefreshToken();
        return this;
    }

}
