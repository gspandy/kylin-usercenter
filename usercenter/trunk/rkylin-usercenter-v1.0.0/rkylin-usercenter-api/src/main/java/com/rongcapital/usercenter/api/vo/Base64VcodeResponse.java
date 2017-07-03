package com.rongcapital.usercenter.api.vo;

import java.io.Serializable;

public class Base64VcodeResponse implements Serializable{
    
    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    
    
    private String vcode;
    private String xAuthToken;
    
    
    
    public Base64VcodeResponse() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Base64VcodeResponse(String vcode, String xAuthToken) {
        super();
        this.vcode = vcode;
        this.xAuthToken = xAuthToken;
    }
    @Override
    public String toString() {
        return "Base64VcodeResponse [vcode=" + vcode + ", xAuthToken=" + xAuthToken + "]";
    }
    public String getVcode() {
        return vcode;
    }
    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
    public String getxAuthToken() {
        return xAuthToken;
    }
    public void setxAuthToken(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }
    
    

}
