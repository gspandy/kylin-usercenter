package com.rongcapital.usercenter.api.vo;

import java.io.Serializable;
import java.util.Date;

public class OpenAccountPerson implements Serializable {
    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    private String personChnName;
    private String personEngName;
    private Integer personType;
    private String personSex;
    private String birthday;
    private String certificateType;
    private String certificateNumber;
    private String mobileTel;
    private String fixTel;
    private String email;
    private String post;
    private String address;
    private String remark;
    private String userId;
    private String rootInstCd;
    private String productId;
    private String roleCode;
    private String userName;
    private String accountCode;
    private String whetherRealName;
    private String statusId;
    private Date createdTime;
    private Date updatedTime;
    private Date startTime;
    private Date endTime;
    private Integer PageSize;
    private Integer PageNum;
    private String referUserId;

    public String getReferUserId() {
        return this.referUserId;
    }

    public void setReferUserId(String referUserId) {
        this.referUserId = referUserId;
    }

    public Date getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return this.updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getStatusId() {
        return this.statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPageSize() {
        return this.PageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.PageSize = pageSize;
    }

    public Integer getPageNum() {
        return this.PageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.PageNum = pageNum;
    }

    public String getPersonChnName() {
        return this.personChnName;
    }

    public void setPersonChnName(String personChnName) {
        this.personChnName = personChnName;
    }

    public String getPersonEngName() {
        return this.personEngName;
    }

    public void setPersonEngName(String personEngName) {
        this.personEngName = personEngName;
    }

    public Integer getPersonType() {
        return this.personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public String getPersonSex() {
        return this.personSex;
    }

    public void setPersonSex(String personSex) {
        this.personSex = personSex;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCertificateType() {
        return this.certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNumber() {
        return this.certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getMobileTel() {
        return this.mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public String getFixTel() {
        return this.fixTel;
    }

    public void setFixTel(String fixTel) {
        this.fixTel = fixTel;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost() {
        return this.post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRootInstCd() {
        return this.rootInstCd;
    }

    public void setRootInstCd(String rootInstCd) {
        this.rootInstCd = rootInstCd;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountCode() {
        return this.accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getWhetherRealName() {
        return this.whetherRealName;
    }

    public void setWhetherRealName(String whetherRealName) {
        this.whetherRealName = whetherRealName;
    }

    @Override
    public String toString() {
        return "OpenAccountPerson [personChnName=" + personChnName + ", personEngName=" + personEngName
                + ", personType=" + personType + ", personSex=" + personSex + ", birthday=" + birthday
                + ", certificateType=" + certificateType + ", certificateNumber=" + certificateNumber + ", mobileTel="
                + mobileTel + ", fixTel=" + fixTel + ", email=" + email + ", post=" + post + ", address=" + address
                + ", remark=" + remark + ", userId=" + userId + ", rootInstCd=" + rootInstCd + ", productId="
                + productId + ", roleCode=" + roleCode + ", userName=" + userName + ", accountCode=" + accountCode
                + ", whetherRealName=" + whetherRealName + ", statusId=" + statusId + ", createdTime=" + createdTime
                + ", updatedTime=" + updatedTime + ", startTime=" + startTime + ", endTime=" + endTime + ", PageSize="
                + PageSize + ", PageNum=" + PageNum + ", referUserId=" + referUserId + "]";
    }
    
}
