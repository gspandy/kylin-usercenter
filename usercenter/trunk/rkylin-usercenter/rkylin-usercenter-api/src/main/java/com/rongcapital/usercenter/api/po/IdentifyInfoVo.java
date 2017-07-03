package com.rongcapital.usercenter.api.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description:
 * @author: bihf
 * @CreateDate: 2016年12月21日
 * @version: V1.0
 */
public class IdentifyInfoVo implements Serializable {



 private static final long serialVersionUID = 1209862271383735785L;



    
    private String userToken;//用户token
    
    private String realName;//真实姓名

    private String idcardNo;//身份证号/营业执照号
    
    private String idCardNoAddress; //身份证地址
    
    private String national; //国籍
    
    private String ethnic ;  //民族
    
    private String policeStation;//所属派出所
    
    private String idcardNoExpireTime;//证件号过期时间

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getIdCardNoAddress() {
        return idCardNoAddress;
    }

    public void setIdCardNoAddress(String idCardNoAddress) {
        this.idCardNoAddress = idCardNoAddress;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getIdcardNoExpireTime() {
        return idcardNoExpireTime;
    }

    public void setIdcardNoExpireTime(String idcardNoExpireTime) {
        this.idcardNoExpireTime = idcardNoExpireTime;
    }

    @Override
    public String toString() {
        return "IdentifyInfoVo [userToken=" + userToken + ", realName=" + realName + ", idcardNo=" + idcardNo
                + ", idCardNoAddress=" + idCardNoAddress + ", national=" + national + ", ethnic=" + ethnic
                + ", policeStation=" + policeStation + ", idcardNoExpireTime=" + idcardNoExpireTime + "]";
    }

  
}
