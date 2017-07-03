package com.rongcapital.usercenter.provider.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息 Description:
 * 
 * @author: Administrator
 * @CreateDate: 2016年1月25日
 * @version: V1.0
 */
public class UserInfoPo implements Serializable {

    private static final long serialVersionUID = 1209862271383735785L;



    /**
     * 用户类型
     */
    private Integer userType;
    private String salt;

    private String userCode;

    private Long userInfoId;
    
    private String userId;
    /**
     * 用户名
     */
    private String userName;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 证件类型
     */
    private String idCardType;
    /**
     * 密码
     */
    private String pwd;

    /**
     * 真空姓名
     */
    private String userRealName;

    /**
     * 机构ID
     */
    private String orgId;

    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 产品ID
     */
    private String proId;
    private String idCard;

    private int status;

    public String getIdCard() {
        return idCard;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

   

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }
    
    

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    
}
