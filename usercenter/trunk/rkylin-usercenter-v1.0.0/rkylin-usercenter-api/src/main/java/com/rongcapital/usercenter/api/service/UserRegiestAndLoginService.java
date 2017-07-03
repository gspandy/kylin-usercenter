package com.rongcapital.usercenter.api.service;

import com.rongcapital.usercenter.api.exception.RegiesterException;
import com.rongcapital.usercenter.api.vo.Base64VcodeResponse;
import com.rongcapital.usercenter.api.vo.BaseResultVo;
import com.rongcapital.usercenter.api.vo.LoginInfo;
import com.rongcapital.usercenter.api.vo.LoginResultVo;
import com.rongcapital.usercenter.api.vo.UserRegiesterInfo;

public interface UserRegiestAndLoginService {
    
    //注意考虑终端类型
    /**
     * Discription:注册，
     *                      个人注册使用：com.rongcapital.usercenter.api.vo.PersonRegiesterInfo
     *                      企业注册使用：com.rongcapital.usercenter.api.vo.CompanyRegiesterInfo
     * 
     * @param regiesterInfo 注册信息
     * @return BaseResultVo<Boolean>
     * @author LCER
     * @since 2016年10月21日
     */
    public BaseResultVo<Boolean> regiest(UserRegiesterInfo regiesterInfo) throws RegiesterException;
    
    /**
     * Discription:登录
     * @param loginInfo 登录信息
     * @return BaseResultVo<String>
     * @author LCER
     * @since 2016年10月21日
     */
    public LoginResultVo<String> login(LoginInfo loginInfo);
    
    
    
    /**
     * Discription:获取验证码
     * @param appCode
     * @param xAuthToken
     * @return BaseResultVo<Base64VcodeResponse>
     * @author LCER
     * @since 2016年10月28日
     */
    public BaseResultVo<Base64VcodeResponse> getBase64Vcode(String appCode, String xAuthToken);

}
