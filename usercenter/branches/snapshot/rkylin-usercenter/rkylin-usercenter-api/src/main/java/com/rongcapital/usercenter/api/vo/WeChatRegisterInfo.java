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

    /** 身份证号 */
    private String idNumber;

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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "WeChatRegisterInfo [weChatId=" + weChatId + ", publicNumberId=" + publicNumberId + ", openId=" + openId
                + ", idNumber=" + idNumber + "]";
    }

    

}
