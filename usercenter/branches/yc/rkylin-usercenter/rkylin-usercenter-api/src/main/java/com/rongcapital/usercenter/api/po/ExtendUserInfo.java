
package com.rongcapital.usercenter.api.po;

public class ExtendUserInfo extends UserInfoPo {
    

    /**
     * Description:
     */
    private static final long serialVersionUID = 2623220675886748588L;

    /** 移动电话 */
    private String mobilePhone;

    /** 邮箱 */
    private String email;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PersonInfo [mobilePhone=" + mobilePhone + ", email=" + email + "]";
    }
     

}