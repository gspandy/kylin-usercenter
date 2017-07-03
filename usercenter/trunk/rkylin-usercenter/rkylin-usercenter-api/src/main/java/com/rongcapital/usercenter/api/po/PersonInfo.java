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
 * 
 * @author: Administrator
 * @CreateDate: 2016-8-31
 * @version: V1.0
 */
public class PersonInfo implements Serializable {
    /** 用户ID */
    private Long id;

    private Long userInfoId;

    /** 用户中文名字 */
    private String userCname;

    /** 用户英文名字 */
    private String userEname;

    /** 用户类型 */
    private Byte userType;

    /** 用户性别 */
    private String gender;

    /** 用户生日 */
    private Date birthday;

    /** 身份证号 */
    private String idNumber;
    /** 身份证有效期 */
    private String idNumberExpireTime;
    /** 身份证上地址 */
    private String idNumberAddress;
    /** 身份证 民族*/
    private String ethnic;
    /** 身份证 发证机关*/
    private String policeStation;

    /** 用户地址 */
    private String address;

    /** 移动电话 */
    private String mobilePhone;

    /** 固定电话 */
    private String fixedPhone;

    /** 邮箱 */
    private String email;

    /** 传真号 */
    private String handlerFax;

    /** 国家 */
    private String national;

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String district;

    /** 邮政编码 */
    private String zipCode;

    /** 学历 */
    private String education;

    /** 政治面貌 */
    private String politicalStatus;

    /** 所属行业 */
    private String industry;

    /** 公司名称 */
    private String companyName;

    /** 公司性质 */
    private String companyAttribute;

    /** 公司电话 */
    private String companyPhone;

    /** 工作性质 */
    private String jobAttribute;

    /** 职位 */
    private String occupation;

    /** 是否结婚 */
    private String marriage;

    /** 家庭人口数量 */
    private Byte familySize;

    /** 个人收入 */
    private String personIncome;

    /** 家庭收入 */
    private String familyIncome;

    /** 用户来源 */
    private String comeFrom;

    /** QQ号 */
    private String qqNumber;

    /** 微信号 */
    private String wechatNumber;

    /** 微博号 */
    private String weboNumber;

    /** 实名验证类型 */
    private Byte isauthor;

    /** 备用1 */
    private String otherinfo1;

    /** 备用2 */
    private String otherinfo2;

    /** 备用3 */
    private String otherinfo3;

    /** 用户备注 */
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

    public String getUserCname() {
        return userCname;
    }

    public void setUserCname(String userCname) {
        this.userCname = userCname == null ? null : userCname.trim();
    }

    public String getUserEname() {
        return userEname;
    }

    public void setUserEname(String userEname) {
        this.userEname = userEname == null ? null : userEname.trim();
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone == null ? null : fixedPhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getHandlerFax() {
        return handlerFax;
    }

    public void setHandlerFax(String handlerFax) {
        this.handlerFax = handlerFax == null ? null : handlerFax.trim();
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national == null ? null : national.trim();
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus == null ? null : politicalStatus.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyAttribute() {
        return companyAttribute;
    }

    public void setCompanyAttribute(String companyAttribute) {
        this.companyAttribute = companyAttribute == null ? null : companyAttribute.trim();
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone == null ? null : companyPhone.trim();
    }

    public String getJobAttribute() {
        return jobAttribute;
    }

    public void setJobAttribute(String jobAttribute) {
        this.jobAttribute = jobAttribute == null ? null : jobAttribute.trim();
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation == null ? null : occupation.trim();
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage == null ? null : marriage.trim();
    }

    public Byte getFamilySize() {
        return familySize;
    }

    public void setFamilySize(Byte familySize) {
        this.familySize = familySize;
    }

    public String getPersonIncome() {
        return personIncome;
    }

    public void setPersonIncome(String personIncome) {
        this.personIncome = personIncome == null ? null : personIncome.trim();
    }

    public String getFamilyIncome() {
        return familyIncome;
    }

    public void setFamilyIncome(String familyIncome) {
        this.familyIncome = familyIncome == null ? null : familyIncome.trim();
    }

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom == null ? null : comeFrom.trim();
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber == null ? null : qqNumber.trim();
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber == null ? null : wechatNumber.trim();
    }

    public String getWeboNumber() {
        return weboNumber;
    }

    public void setWeboNumber(String weboNumber) {
        this.weboNumber = weboNumber == null ? null : weboNumber.trim();
    }

    public Byte getIsauthor() {
        return isauthor;
    }

    public void setIsauthor(Byte isauthor) {
        this.isauthor = isauthor;
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

    public String getIdNumberExpireTime() {
        return idNumberExpireTime;
    }

    public void setIdNumberExpireTime(String idNumberExpireTime) {
        this.idNumberExpireTime = idNumberExpireTime;
    }

    public String getIdNumberAddress() {
        return idNumberAddress;
    }

    public void setIdNumberAddress(String idNumberAddress) {
        this.idNumberAddress = idNumberAddress;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    @Override
    public String toString() {
        return "PersonInfo [id=" + id + ", userInfoId=" + userInfoId + ", userCname=" + userCname + ", userEname="
                + userEname + ", userType=" + userType + ", gender=" + gender + ", birthday=" + birthday
                + ", idNumber=" + idNumber + ", idNumberExpireTime=" + idNumberExpireTime + ", idNumberAddress="
                + idNumberAddress + ", ethnic=" + ethnic + ", policeStation=" + policeStation + ", address=" + address
                + ", mobilePhone=" + mobilePhone + ", fixedPhone=" + fixedPhone + ", email=" + email + ", handlerFax="
                + handlerFax + ", national=" + national + ", province=" + province + ", city=" + city + ", district="
                + district + ", zipCode=" + zipCode + ", education=" + education + ", politicalStatus="
                + politicalStatus + ", industry=" + industry + ", companyName=" + companyName + ", companyAttribute="
                + companyAttribute + ", companyPhone=" + companyPhone + ", jobAttribute=" + jobAttribute
                + ", occupation=" + occupation + ", marriage=" + marriage + ", familySize=" + familySize
                + ", personIncome=" + personIncome + ", familyIncome=" + familyIncome + ", comeFrom=" + comeFrom
                + ", qqNumber=" + qqNumber + ", wechatNumber=" + wechatNumber + ", weboNumber=" + weboNumber
                + ", isauthor=" + isauthor + ", otherinfo1=" + otherinfo1 + ", otherinfo2=" + otherinfo2
                + ", otherinfo3=" + otherinfo3 + ", remark=" + remark + ", createdTime=" + createdTime
                + ", updatedTime=" + updatedTime + "]";
    }

    
    
}