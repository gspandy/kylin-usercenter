package com.rongcapital.usercenter.provider.vo;

import java.io.Serializable;

public class UserLoginHistoryPo implements Serializable {

    public UserLoginHistoryPo() {

    }

    public UserLoginHistoryPo(String loginIp, Long userInfoId, Integer loginType, Integer loginSource) {
        this.loginIp = loginIp;
        this.userInfoId = userInfoId;
        this.loginType = loginType;
        this.longSource = loginSource;
    }

    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    private Long userInfoId;

    /** 用户ID */
    private Long userId;

    /** IP */
    private String loginIp;

    /** 类型 1登陆 2退出 */
    private Integer loginType;

    /** 用户登陆来源 1 andird 2 ios 3 pc */
    private Integer longSource;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public Integer getLongSource() {
        return longSource;
    }

    public void setLongSource(Integer longSource) {
        this.longSource = longSource;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

}
