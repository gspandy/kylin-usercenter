package com.rongcapital.usercenter.api.vo;

import java.io.Serializable;

public abstract class UserRegiesterInfo implements Serializable{

    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Description:应用编码
     */
    private String appCode;

    /**
     * Description:用户名
     */
    private String userName;
    /**
     * 用户ID，若老数据则存在，新数据为系统生成
     */
    private String userId;
    /**
     * 机构
     */
    private String orgCode;
    
    /**
     * Description:密码
     */
    private String password;
    
    /**
     * Description:注册类型
     */
    private RegiesterType regiesterType;
    
   
    /**
     * Description:验证码
     */
    private String vcode;
    
    
    
    
    
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




    public String getUserName() {
        return userName;
    }




    public void setUserName(String userName) {
        this.userName = userName;
    }




    public String getPassword() {
        return password;
    }




    public void setPassword(String password) {
        this.password = password;
    }




    public RegiesterType getRegiesterType() {
        return regiesterType;
    }




    public void setRegiesterType(RegiesterType regiesterType) {
        this.regiesterType = regiesterType;
    }




  




    public String getOrgCode() {
        return orgCode;
    }




    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }









    public String getUserId() {
        return userId;
    }




    public void setUserId(String userId) {
        this.userId = userId;
    }









    public enum RegiesterType{
        PERSON(2),COMPANY(1),WECHAT(3);
        private Integer type;
        private RegiesterType(Integer type){
            this.type = type;
        }
        public Integer value(){
            return this.type;
        }
    }
    
    
}
