package com.rongcapital.usercenter.api.vo;

public class PassWordHandleVo extends LoginInfo{

    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
     
    /**
     * 密码操作类型  1,校验  2,重置 3,修改
     */
    private String pwdTye;

    public String getPwdTye() {
        return pwdTye;
    }

    public void setPwdTye(String pwdTye) {
        this.pwdTye = pwdTye;
    }

    @Override
    public String toString() {
        return "PassWordHandleVo [pwdTye=" + pwdTye + "]";
    }
    
    
    
  
}
