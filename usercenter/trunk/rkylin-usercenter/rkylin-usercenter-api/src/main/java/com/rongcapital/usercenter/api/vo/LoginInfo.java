package com.rongcapital.usercenter.api.vo;

import java.io.Serializable;

public class LoginInfo implements Serializable {

    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Description:应用编码
     */
    private String appCode;
    /**
     * Description:终端类型
     */
    private TerminalType terminalType;
    /**
     * Description:登录名
     */
    private String loginName;
    
    private String orgCode;
    /**
     * Description:密码
     */
    private String password;
    /**
     * Description:终端编号
     */
    private String terminalNo;
    
    /**
     * Description:验证码
     */
    private String vcode;
    
    
    /**
     * Description:保持回话token，这个是如果需要验证码登录，验证码接口返回的参数
     */
    private String xAuthToken;

    
    
    public String getxAuthToken() {
        return xAuthToken;
    }

    public void setxAuthToken(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public TerminalType getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(TerminalType terminalType) {
        this.terminalType = terminalType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public enum TerminalType {
        PC(3), ANDROID(1), ISO(2);
        private Integer type;
        private TerminalType(Integer type){
            this.type = type;
        }
        public Integer value(){
            return this.type;
        }
        @Override
        public String toString() {
            return type.toString();
        }
    }

    @Override
    public String toString() {
        return "LoginInfo [appCode=" + appCode + ", terminalType=" + terminalType + ", loginName=" + loginName
                + ", orgCode=" + orgCode + ", password=" + password + ", terminalNo=" + terminalNo + ", vcode=" + vcode
                + ", xAuthToken=" + xAuthToken + "]";
    }
    
}
