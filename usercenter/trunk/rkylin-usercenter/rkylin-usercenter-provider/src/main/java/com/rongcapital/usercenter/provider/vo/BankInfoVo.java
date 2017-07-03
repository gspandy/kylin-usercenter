package com.rongcapital.usercenter.provider.vo;

import java.io.Serializable;

public class BankInfoVo implements Serializable {

    public BankInfoVo() {

    }

    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    // /**
    // * 账户ID
    // */
    // private String finAccountId;
    /**
     * 银行卡号
     */
    private String bankCardNum;

    public String getBankCardNumPwd() {
        if (null != this.bankCardNum && !"".equals(this.bankCardNum)) {
            int length = this.bankCardNum.length();
            return new StringBuffer("****").append(this.bankCardNum.substring(length - 4, length)).toString();
        }
        return null;
    }

    /**
     * 银行卡类型
     */
    private String bankType;

    /**
     * 银行卡所属行
     */
    private String bankName;

    /**
     * 银行code
     */
    private String bankCode;
    private String bankBranchName;
    private String bankBranch;

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    /**
     * 状态0失效 ，1正常（成功） ，2待审核 ，3正在审核 ，4，审核无效
     */
    private Integer status;

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    // /**
    // * 预留号码
    // */
    // private String tel;

    /**
     * 持卡人姓名
     */
    // private String userRealName;

    /**
     * 持卡人身份证
     */
    // private String idCard;

    // public String getFinAccountId() {
    // return finAccountId;
    // }
    //
    // public void setFinAccountId(String finAccountId) {
    // this.finAccountId = finAccountId;
    // }

    // public String getTel() {
    // return tel;
    // }
    //
    // public void setTel(String tel) {
    // this.tel = tel;
    // }

    // public String getUserRealName() {
    // return userRealName;
    // }
    //
    // public void setUserRealName(String userRealName) {
    // this.userRealName = userRealName;
    // }

    // public String getIdCard() {
    // return idCard;
    // }
    //
    // public void setIdCard(String idCard) {
    // this.idCard = idCard;
    // }

}
