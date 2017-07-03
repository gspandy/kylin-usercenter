package com.rongcapital.usercenter.api.vo;

import java.io.Serializable;
import java.util.Date;

public class OpenAccountCompany implements Serializable {
    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    private String companyName;
    private String companyShortName;
    private String mcc;
    private String post;
    private String connect;
    private String address;
    private String buslince;
    private String acuntOpnLince;
    private String companyCode;
    private String companyType;
    private String taxregCard1;
    private String taxregCard2;
    private String organCertificate;
    private String corporateName;
    private String corporateIdentity;
    private String busPlaceCtf;
    private String loanCard;
    private String remark;
    private String userId;
    private String rootInstCd;
    private String productId;
    private String roleCode;
    private String userName;
    private String accountCode;
    private String whetherRealName;
    private Date startTime;
    private Date endTime;
    private Integer PageSize;
    private Integer PageNum;
    private Date createdTime;
    private Date updatedTime;
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

    public String getCompanyType() {
        return this.companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyShortName() {
        return this.companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getMcc() {
        return this.mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getPost() {
        return this.post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getConnect() {
        return this.connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuslince() {
        return this.buslince;
    }

    public void setBuslince(String buslince) {
        this.buslince = buslince;
    }

    public String getAcuntOpnLince() {
        return this.acuntOpnLince;
    }

    public void setAcuntOpnLince(String acuntOpnLince) {
        this.acuntOpnLince = acuntOpnLince;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getTaxregCard1() {
        return this.taxregCard1;
    }

    public void setTaxregCard1(String taxregCard1) {
        this.taxregCard1 = taxregCard1;
    }

    public String getTaxregCard2() {
        return this.taxregCard2;
    }

    public void setTaxregCard2(String taxregCard2) {
        this.taxregCard2 = taxregCard2;
    }

    public String getOrganCertificate() {
        return this.organCertificate;
    }

    public void setOrganCertificate(String organCertificate) {
        this.organCertificate = organCertificate;
    }

    public String getCorporateName() {
        return this.corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getCorporateIdentity() {
        return this.corporateIdentity;
    }

    public void setCorporateIdentity(String corporateIdentity) {
        this.corporateIdentity = corporateIdentity;
    }

    public String getBusPlaceCtf() {
        return this.busPlaceCtf;
    }

    public void setBusPlaceCtf(String busPlaceCtf) {
        this.busPlaceCtf = busPlaceCtf;
    }

    public String getLoanCard() {
        return this.loanCard;
    }

    public void setLoanCard(String loanCard) {
        this.loanCard = loanCard;
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
        return "OpenAccountCompany [companyName=" + companyName + ", companyShortName=" + companyShortName + ", mcc="
                + mcc + ", post=" + post + ", connect=" + connect + ", address=" + address + ", buslince=" + buslince
                + ", acuntOpnLince=" + acuntOpnLince + ", companyCode=" + companyCode + ", companyType=" + companyType
                + ", taxregCard1=" + taxregCard1 + ", taxregCard2=" + taxregCard2 + ", organCertificate="
                + organCertificate + ", corporateName=" + corporateName + ", corporateIdentity=" + corporateIdentity
                + ", busPlaceCtf=" + busPlaceCtf + ", loanCard=" + loanCard + ", remark=" + remark + ", userId="
                + userId + ", rootInstCd=" + rootInstCd + ", productId=" + productId + ", roleCode=" + roleCode
                + ", userName=" + userName + ", accountCode=" + accountCode + ", whetherRealName=" + whetherRealName
                + ", startTime=" + startTime + ", endTime=" + endTime + ", PageSize=" + PageSize + ", PageNum="
                + PageNum + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + ", referUserId="
                + referUserId + "]";
    }

}
