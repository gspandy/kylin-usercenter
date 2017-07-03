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
 * @CreateDate: 2016-8-31
 * @version: V1.0
 */
public class UserInfoDetail implements Serializable {
    
    private Long userInfoId;

    /** 用户ID */
    private String userId;
    
    private String userCode;
    
    private String loginName;
    /**
     * 机构号
     */
    private String orgCode;

    private String caasUserCode;
    
    /** 用户类型  1商户  2个人 */
    private Integer userType;
    

    /** 用户状态  1正常  0禁用  9删除 */
    private Integer status;

    private Integer version;

    /** 记录创建时间 */
    private Date createdTime;

    /** 记录更新时间 */
    private Date updatedTime;

    private PersonInfo personInfo;
    
    private CompanyInfo companyInfo;
    
    private Date lastLoginTime;  //最后登录时间
    
    private Integer isIdentify;//是否实名制
    
    private static final long serialVersionUID = 1L;

    
    

  

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }


    public Integer getIsIdentify() {
        return isIdentify;
    }

    public void setIsIdentify(Integer isIdentify) {
        this.isIdentify = isIdentify;
    }

    public String getCaasUserCode() {
        return caasUserCode;
    }

    public void setCaasUserCode(String caasUserCode) {
        this.caasUserCode = caasUserCode;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }


    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

   

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }



 
   
    @Override
    public String toString() {
        return "UserInfo [userInfoId=" + userInfoId + ", userId=" + userId + ", userCode=" + userCode + ", orgCode="
                + orgCode + ", caasUserCode=" + caasUserCode + ", userType=" + userType + ", status=" + status
                + ", version=" + version + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime
                + ", personInfo=" + personInfo + ", companyInfo=" + companyInfo + ", lastLoginTime=" + lastLoginTime
                + ", isIdentify=" + isIdentify + "]";
    }





    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }





    public enum Status{
        INIT(2),CAAS_ERROR(3),CAAS_SUCCESS(4),SUCCESS(1);
        private Integer status;
        private Status(Integer status){
            this.status = status;
        }
        public Integer value(){
            return this.status;
        }
        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return this.status.toString();
        }
    }
}