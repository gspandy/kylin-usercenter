/**
 * <p>Title: ${file_name}</p>
 * <p>Description:${project_name} </p>
 * <p>Copyright:${date} </p>
 * <p>Company: rongshu</p>
 * <p>author: ${user}</p>
 * <p>package: ${package_name}</p>
 * @version v1.0.0
 */
package com.rongcapital.usercenter.api.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * @author: Administrator
 * @CreateDate: 2016-9-1
 * @version: V1.0
 */
public class CompanyInfo implements Serializable {
    private Long id;

    private Long userInfoId;

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

    /** 备用1 */
    private String otherinfo1;

    /** 备用2 */
    private String otherinfo2;

    /** 备用3 */
    private String otherinfo3;

    /** 备注 */
    private String remark;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName == null ? null : companyShortName.trim();
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
        this.mcc = mcc == null ? null : mcc.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getContactsInfo() {
        return contactsInfo;
    }

    public void setContactsInfo(String contactsInfo) {
        this.contactsInfo = contactsInfo == null ? null : contactsInfo.trim();
    }

    public String getRegistFinacnce() {
        return registFinacnce;
    }

    public void setRegistFinacnce(String registFinacnce) {
        this.registFinacnce = registFinacnce == null ? null : registFinacnce.trim();
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members == null ? null : members.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getBuslince() {
        return buslince;
    }

    public void setBuslince(String buslince) {
        this.buslince = buslince == null ? null : buslince.trim();
    }

    public String getAcuntOpnLince() {
        return acuntOpnLince;
    }

    public void setAcuntOpnLince(String acuntOpnLince) {
        this.acuntOpnLince = acuntOpnLince == null ? null : acuntOpnLince.trim();
    }

    public String getTaxregCard1() {
        return taxregCard1;
    }

    public void setTaxregCard1(String taxregCard1) {
        this.taxregCard1 = taxregCard1 == null ? null : taxregCard1.trim();
    }

    public String getTaxregCard2() {
        return taxregCard2;
    }

    public void setTaxregCard2(String taxregCard2) {
        this.taxregCard2 = taxregCard2 == null ? null : taxregCard2.trim();
    }

    public String getOrganCertificate() {
        return organCertificate;
    }

    public void setOrganCertificate(String organCertificate) {
        this.organCertificate = organCertificate == null ? null : organCertificate.trim();
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName == null ? null : corporateName.trim();
    }

    public String getCorporateIdentity() {
        return corporateIdentity;
    }

    public void setCorporateIdentity(String corporateIdentity) {
        this.corporateIdentity = corporateIdentity == null ? null : corporateIdentity.trim();
    }

    public String getCorporateTel() {
        return corporateTel;
    }

    public void setCorporateTel(String corporateTel) {
        this.corporateTel = corporateTel == null ? null : corporateTel.trim();
    }

    public String getCorporateMail() {
        return corporateMail;
    }

    public void setCorporateMail(String corporateMail) {
        this.corporateMail = corporateMail == null ? null : corporateMail.trim();
    }

    public String getBusPlaceCtf() {
        return busPlaceCtf;
    }

    public void setBusPlaceCtf(String busPlaceCtf) {
        this.busPlaceCtf = busPlaceCtf == null ? null : busPlaceCtf.trim();
    }

    public String getLoanCard() {
        return loanCard;
    }

    public void setLoanCard(String loanCard) {
        this.loanCard = loanCard == null ? null : loanCard.trim();
    }

    public String getOtherinfo1() {
        return otherinfo1;
    }

    public void setOtherinfo1(String otherinfo1) {
        this.otherinfo1 = otherinfo1 == null ? null : otherinfo1.trim();
    }

    public String getOtherinfo2() {
        return otherinfo2;
    }

    public void setOtherinfo2(String otherinfo2) {
        this.otherinfo2 = otherinfo2 == null ? null : otherinfo2.trim();
    }

    public String getOtherinfo3() {
        return otherinfo3;
    }

    public void setOtherinfo3(String otherinfo3) {
        this.otherinfo3 = otherinfo3 == null ? null : otherinfo3.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userInfoId=").append(userInfoId);
        sb.append(", companyName=").append(companyName);
        sb.append(", companyShortName=").append(companyShortName);
        sb.append(", companyType=").append(companyType);
        sb.append(", mcc=").append(mcc);
        sb.append(", post=").append(post);
        sb.append(", fax=").append(fax);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", contacts=").append(contacts);
        sb.append(", contactsInfo=").append(contactsInfo);
        sb.append(", registFinacnce=").append(registFinacnce);
        sb.append(", members=").append(members);
        sb.append(", address=").append(address);
        sb.append(", website=").append(website);
        sb.append(", buslince=").append(buslince);
        sb.append(", acuntOpnLince=").append(acuntOpnLince);
        sb.append(", taxregCard1=").append(taxregCard1);
        sb.append(", taxregCard2=").append(taxregCard2);
        sb.append(", organCertificate=").append(organCertificate);
        sb.append(", corporateName=").append(corporateName);
        sb.append(", corporateIdentity=").append(corporateIdentity);
        sb.append(", corporateTel=").append(corporateTel);
        sb.append(", corporateMail=").append(corporateMail);
        sb.append(", busPlaceCtf=").append(busPlaceCtf);
        sb.append(", loanCard=").append(loanCard);
        sb.append(", otherinfo1=").append(otherinfo1);
        sb.append(", otherinfo2=").append(otherinfo2);
        sb.append(", otherinfo3=").append(otherinfo3);
        sb.append(", remark=").append(remark);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CompanyInfo other = (CompanyInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserInfoId() == null ? other.getUserInfoId() == null : this.getUserInfoId().equals(other.getUserInfoId()))
            && (this.getCompanyName() == null ? other.getCompanyName() == null : this.getCompanyName().equals(other.getCompanyName()))
            && (this.getCompanyShortName() == null ? other.getCompanyShortName() == null : this.getCompanyShortName().equals(other.getCompanyShortName()))
            && (this.getCompanyType() == null ? other.getCompanyType() == null : this.getCompanyType().equals(other.getCompanyType()))
            && (this.getMcc() == null ? other.getMcc() == null : this.getMcc().equals(other.getMcc()))
            && (this.getPost() == null ? other.getPost() == null : this.getPost().equals(other.getPost()))
            && (this.getFax() == null ? other.getFax() == null : this.getFax().equals(other.getFax()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getContacts() == null ? other.getContacts() == null : this.getContacts().equals(other.getContacts()))
            && (this.getContactsInfo() == null ? other.getContactsInfo() == null : this.getContactsInfo().equals(other.getContactsInfo()))
            && (this.getRegistFinacnce() == null ? other.getRegistFinacnce() == null : this.getRegistFinacnce().equals(other.getRegistFinacnce()))
            && (this.getMembers() == null ? other.getMembers() == null : this.getMembers().equals(other.getMembers()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getWebsite() == null ? other.getWebsite() == null : this.getWebsite().equals(other.getWebsite()))
            && (this.getBuslince() == null ? other.getBuslince() == null : this.getBuslince().equals(other.getBuslince()))
            && (this.getAcuntOpnLince() == null ? other.getAcuntOpnLince() == null : this.getAcuntOpnLince().equals(other.getAcuntOpnLince()))
            && (this.getTaxregCard1() == null ? other.getTaxregCard1() == null : this.getTaxregCard1().equals(other.getTaxregCard1()))
            && (this.getTaxregCard2() == null ? other.getTaxregCard2() == null : this.getTaxregCard2().equals(other.getTaxregCard2()))
            && (this.getOrganCertificate() == null ? other.getOrganCertificate() == null : this.getOrganCertificate().equals(other.getOrganCertificate()))
            && (this.getCorporateName() == null ? other.getCorporateName() == null : this.getCorporateName().equals(other.getCorporateName()))
            && (this.getCorporateIdentity() == null ? other.getCorporateIdentity() == null : this.getCorporateIdentity().equals(other.getCorporateIdentity()))
            && (this.getCorporateTel() == null ? other.getCorporateTel() == null : this.getCorporateTel().equals(other.getCorporateTel()))
            && (this.getCorporateMail() == null ? other.getCorporateMail() == null : this.getCorporateMail().equals(other.getCorporateMail()))
            && (this.getBusPlaceCtf() == null ? other.getBusPlaceCtf() == null : this.getBusPlaceCtf().equals(other.getBusPlaceCtf()))
            && (this.getLoanCard() == null ? other.getLoanCard() == null : this.getLoanCard().equals(other.getLoanCard()))
            && (this.getOtherinfo1() == null ? other.getOtherinfo1() == null : this.getOtherinfo1().equals(other.getOtherinfo1()))
            && (this.getOtherinfo2() == null ? other.getOtherinfo2() == null : this.getOtherinfo2().equals(other.getOtherinfo2()))
            && (this.getOtherinfo3() == null ? other.getOtherinfo3() == null : this.getOtherinfo3().equals(other.getOtherinfo3()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserInfoId() == null) ? 0 : getUserInfoId().hashCode());
        result = prime * result + ((getCompanyName() == null) ? 0 : getCompanyName().hashCode());
        result = prime * result + ((getCompanyShortName() == null) ? 0 : getCompanyShortName().hashCode());
        result = prime * result + ((getCompanyType() == null) ? 0 : getCompanyType().hashCode());
        result = prime * result + ((getMcc() == null) ? 0 : getMcc().hashCode());
        result = prime * result + ((getPost() == null) ? 0 : getPost().hashCode());
        result = prime * result + ((getFax() == null) ? 0 : getFax().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getContacts() == null) ? 0 : getContacts().hashCode());
        result = prime * result + ((getContactsInfo() == null) ? 0 : getContactsInfo().hashCode());
        result = prime * result + ((getRegistFinacnce() == null) ? 0 : getRegistFinacnce().hashCode());
        result = prime * result + ((getMembers() == null) ? 0 : getMembers().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getWebsite() == null) ? 0 : getWebsite().hashCode());
        result = prime * result + ((getBuslince() == null) ? 0 : getBuslince().hashCode());
        result = prime * result + ((getAcuntOpnLince() == null) ? 0 : getAcuntOpnLince().hashCode());
        result = prime * result + ((getTaxregCard1() == null) ? 0 : getTaxregCard1().hashCode());
        result = prime * result + ((getTaxregCard2() == null) ? 0 : getTaxregCard2().hashCode());
        result = prime * result + ((getOrganCertificate() == null) ? 0 : getOrganCertificate().hashCode());
        result = prime * result + ((getCorporateName() == null) ? 0 : getCorporateName().hashCode());
        result = prime * result + ((getCorporateIdentity() == null) ? 0 : getCorporateIdentity().hashCode());
        result = prime * result + ((getCorporateTel() == null) ? 0 : getCorporateTel().hashCode());
        result = prime * result + ((getCorporateMail() == null) ? 0 : getCorporateMail().hashCode());
        result = prime * result + ((getBusPlaceCtf() == null) ? 0 : getBusPlaceCtf().hashCode());
        result = prime * result + ((getLoanCard() == null) ? 0 : getLoanCard().hashCode());
        result = prime * result + ((getOtherinfo1() == null) ? 0 : getOtherinfo1().hashCode());
        result = prime * result + ((getOtherinfo2() == null) ? 0 : getOtherinfo2().hashCode());
        result = prime * result + ((getOtherinfo3() == null) ? 0 : getOtherinfo3().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
        return result;
    }
}