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
 * @author: Administrator
 * @CreateDate: 2017-2-22
 * @version: V1.0
 */
public class UcOrgMapping implements Serializable {
    /** 自增主键 */
    private Long id;

    /** 登陆名 */
    private String loginName;

    /** 实际机构号 */
    private String orgCodeActual;

    /** 虚拟机构号 */
    private String orgCodeVirtual;
   
    /**当前注册个人企业所属商户**/
    private String  mechantName;
    
 
    private Date createdTime;

    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getOrgCodeActual() {
        return orgCodeActual;
    }

    public void setOrgCodeActual(String orgCodeActual) {
        this.orgCodeActual = orgCodeActual == null ? null : orgCodeActual.trim();
    }

    public String getOrgCodeVirtual() {
        return orgCodeVirtual;
    }

    public void setOrgCodeVirtual(String orgCodeVirtual) {
        this.orgCodeVirtual = orgCodeVirtual == null ? null : orgCodeVirtual.trim();
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
        return "UcOrgMapping [id=" + id + ", loginName=" + loginName + ", orgCodeActual=" + orgCodeActual
                + ", orgCodeVirtual=" + orgCodeVirtual + ", mechantName=" + mechantName + ", createdTime="
                + createdTime + ", updatedTime=" + updatedTime + "]";
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
        UcOrgMapping other = (UcOrgMapping) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLoginName() == null ? other.getLoginName() == null : this.getLoginName().equals(other.getLoginName()))
            && (this.getOrgCodeActual() == null ? other.getOrgCodeActual() == null : this.getOrgCodeActual().equals(other.getOrgCodeActual()))
            && (this.getOrgCodeVirtual() == null ? other.getOrgCodeVirtual() == null : this.getOrgCodeVirtual().equals(other.getOrgCodeVirtual()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLoginName() == null) ? 0 : getLoginName().hashCode());
        result = prime * result + ((getOrgCodeActual() == null) ? 0 : getOrgCodeActual().hashCode());
        result = prime * result + ((getOrgCodeVirtual() == null) ? 0 : getOrgCodeVirtual().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
        return result;
    }

    public String getMechantName() {
        return mechantName;
    }

    public void setMechantName(String mechantName) {
        this.mechantName = mechantName;
    }
}