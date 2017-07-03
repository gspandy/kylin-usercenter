package com.rongcapital.usercenter.api.vo;

import java.util.Date;

public class CompanyRegiesterInfo extends UserRegiesterInfo {

    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;

    /** 企业名称 */
    private String companyName;

    /** 企业简称 */
    private String companyShortName;

    /** 企业类型 */
    private Byte companyType;

    /** 行业编号 */
    private String mcc;

    /** 邮编 */
    private String post;

    /** 传真 */
    private String fax;

    /** 省份 */
    private String province;

    /** 地市 */
    private String city;

    /** 联系人 */
    private String contacts;

    /** 联系方式 */
    private String contactsInfo;

    /** 注册资金 */
    private String registFinacnce;

    /** 员工数 */
    private String members;

    /** 地址 */
    private String address;

    /** 网址 */
    private String website;

    /** 营业执照号 */
    private String buslince;

    /** 开户许可证 */
    private String acuntOpnLince;

    /** 税务登记证1 */
    private String taxregCard1;

    /** 税务登记证2 */
    private String taxregCard2;

    /** 住址机构代码证 */
    private String organCertificate;

    /** 法人姓名 */
    private String corporateName;

    /** 法人身份证 */
    private String corporateIdentity;

    /** 法人电话 */
    private String corporateTel;

    /** 法人邮箱 */
    private String corporateMail;

    /** 经营场所实地认证 */
    private String busPlaceCtf;

    /** 贷款卡 */
    private String loanCard;


    /** 备注 */
    private String remark;

  

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public Byte getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Byte companyType) {
        this.companyType = companyType;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsInfo() {
        return contactsInfo;
    }

    public void setContactsInfo(String contactsInfo) {
        this.contactsInfo = contactsInfo;
    }

    public String getRegistFinacnce() {
        return registFinacnce;
    }

    public void setRegistFinacnce(String registFinacnce) {
        this.registFinacnce = registFinacnce;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBuslince() {
        return buslince;
    }

    public void setBuslince(String buslince) {
        this.buslince = buslince;
    }

    public String getAcuntOpnLince() {
        return acuntOpnLince;
    }

    public void setAcuntOpnLince(String acuntOpnLince) {
        this.acuntOpnLince = acuntOpnLince;
    }

    public String getTaxregCard1() {
        return taxregCard1;
    }

    public void setTaxregCard1(String taxregCard1) {
        this.taxregCard1 = taxregCard1;
    }

    public String getTaxregCard2() {
        return taxregCard2;
    }

    public void setTaxregCard2(String taxregCard2) {
        this.taxregCard2 = taxregCard2;
    }

    public String getOrganCertificate() {
        return organCertificate;
    }

    public void setOrganCertificate(String organCertificate) {
        this.organCertificate = organCertificate;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getCorporateIdentity() {
        return corporateIdentity;
    }

    public void setCorporateIdentity(String corporateIdentity) {
        this.corporateIdentity = corporateIdentity;
    }

    public String getCorporateTel() {
        return corporateTel;
    }

    public void setCorporateTel(String corporateTel) {
        this.corporateTel = corporateTel;
    }

    public String getCorporateMail() {
        return corporateMail;
    }

    public void setCorporateMail(String corporateMail) {
        this.corporateMail = corporateMail;
    }

    public String getBusPlaceCtf() {
        return busPlaceCtf;
    }

    public void setBusPlaceCtf(String busPlaceCtf) {
        this.busPlaceCtf = busPlaceCtf;
    }

    public String getLoanCard() {
        return loanCard;
    }

    public void setLoanCard(String loanCard) {
        this.loanCard = loanCard;
    }

  
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public String toString() {
        return "CompanyRegiesterInfo [companyName=" + companyName + ", companyShortName=" + companyShortName
                + ", companyType=" + companyType + ", mcc=" + mcc + ", post=" + post + ", fax=" + fax + ", province="
                + province + ", city=" + city + ", contacts=" + contacts + ", contactsInfo=" + contactsInfo
                + ", registFinacnce=" + registFinacnce + ", members=" + members + ", address=" + address + ", website="
                + website + ", buslince=" + buslince + ", acuntOpnLince=" + acuntOpnLince + ", taxregCard1="
                + taxregCard1 + ", taxregCard2=" + taxregCard2 + ", organCertificate=" + organCertificate
                + ", corporateName=" + corporateName + ", corporateIdentity=" + corporateIdentity + ", corporateTel="
                + corporateTel + ", corporateMail=" + corporateMail + ", busPlaceCtf=" + busPlaceCtf + ", loanCard="
                + loanCard +  ", remark=" + remark 
                + ", appCode()=" + getAppCode() + ", userName()=" + getUserName() + ", password()="
                + getPassword() + ", regiesterType()=" + getRegiesterType() + "]";
    }
    
    
}
