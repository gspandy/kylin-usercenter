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


public class NologinReturnUserInfo implements Serializable {
    

   
    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
   
    
    
    private String  mobilePhone;
    private String  fixedPhone;
    private String  email;
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getFixedPhone() {
        return fixedPhone;
    }
    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "NologinUserInfoVo [mobilePhone=" + mobilePhone + ", fixedPhone=" + fixedPhone + ", email=" + email
                + "]";
    }
    
    


    
    

  

  
}