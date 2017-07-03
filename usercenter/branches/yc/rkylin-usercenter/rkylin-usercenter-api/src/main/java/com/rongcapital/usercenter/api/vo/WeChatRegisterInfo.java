package com.rongcapital.usercenter.api.vo;


public class WeChatRegisterInfo extends UserRegiesterInfo{

    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    /**
     * 微信号Id
     */
    private String weChatId;

    /** 公众号Id*/
    private String publicNumberId;

    /** 用户openId */
    private String openId;

    /** 证件类型*/
    private String certificateType;
    /** 证件号 */
    private String certificateNumber;
    /**手机号**/
    private String teleNum;

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public String getPublicNumberId() {
        return publicNumberId;
    }

    public void setPublicNumberId(String publicNumberId) {
        this.publicNumberId = publicNumberId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    
    public String getTeleNum() {
        return teleNum;
    }

    public void setTeleNum(String teleNum) {
        this.teleNum = teleNum;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    @Override
    public String toString() {
        return "WeChatRegisterInfo [weChatId=" + weChatId + ", publicNumberId=" + publicNumberId + ", openId=" + openId
                + ", certificateType=" + certificateType + ", certificateNumber=" + certificateNumber + ", teleNum="
                + teleNum + "]";
    }

   

}
