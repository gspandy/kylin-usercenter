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
public class UserLogin implements Serializable {
    /** 登陆ID */
    private Long id;

    /** 用户编码(个人或企业) */
    private Long userInfoId;

    /** 密码盐值 */
    private String pwdSalt;

    /** 密码 */
    private String pwdMd;

    /** 用户类型  1商户  2个人 */
    private Integer userType;

    
    /** 登陆名 */
    private String loginName;
    
    private String orgCode;

    /** 记录创建时间 */
    private Date createdTime;

    /** 记录更新时间 */
    private Date updatedTime;

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

    public String getPwdSalt() {
        return pwdSalt;
    }

    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt == null ? null : pwdSalt.trim();
    }

    public String getPwdMd() {
        return pwdMd;
    }

    public void setPwdMd(String pwdMd) {
        this.pwdMd = pwdMd == null ? null : pwdMd.trim();
    }

    
    
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

}