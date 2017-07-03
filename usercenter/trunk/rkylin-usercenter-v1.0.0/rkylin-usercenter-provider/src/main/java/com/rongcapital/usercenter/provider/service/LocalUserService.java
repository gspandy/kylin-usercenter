package com.rongcapital.usercenter.provider.service;

import com.rongcapital.usercenter.provider.po.CompanyInfo;
import com.rongcapital.usercenter.provider.po.PersonInfo;
import com.rongcapital.usercenter.provider.po.UserInfo;
import com.rongcapital.usercenter.provider.po.UserLogin;

public interface LocalUserService {
    
    public void regiestPersonInfo(UserInfo userInfo,PersonInfo personInfo,UserLogin userLogin);
    
    public void regiestCompanyInfo(UserInfo userInfo,CompanyInfo companyInfo,UserLogin userLogin);

    
    public void modifyCaasUserCode(Long userInfoId,String caasUserCode) ;
    
    public void modifyPwd(UserInfo userInfo,String newPassword);
    
    public UserInfo  getUserInfoByLoginName(String loginName);
}
