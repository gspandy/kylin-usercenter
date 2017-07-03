/**
 * <p>Title: ${file_name}</p>
 * <p>Description:${project_name} </p>
 * <p>Copyright:${date} </p>
 * <p>Company: rongshu</p>
 * <p>author: ${user}</p>
 * <p>package: ${package_name}</p>
 * @version v1.0.0
 */
package com.rongcapital.usercenter.provider.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * 
 * @author: Administrator
 * @CreateDate: 2016-8-31
 * @version: V1.0
 */
public class UserLoginHistory implements Serializable {
    /** 主键 */
    private Long id;

    /** 登陆ID */
    private Long userInfoId;

    /** IP */
    private String loginIp;

    /** 类型 1登陆 2退出 */
    private Integer loginType;

    /** 用户登陆来源 1 andird 2 ios 3 pc */
    private Integer longSource;

    /** 记录创建时间 */
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

}