package com.rongcapital.usercenter.provider.vo;

import java.io.Serializable;

public class PersonInfoVo implements Serializable {

 
    private String userCname;
    
    private String idNumber;

    public String getUserCname() {
        return userCname;
    }

    public void setUserCname(String userCname) {
        this.userCname = userCname;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    
    
}
