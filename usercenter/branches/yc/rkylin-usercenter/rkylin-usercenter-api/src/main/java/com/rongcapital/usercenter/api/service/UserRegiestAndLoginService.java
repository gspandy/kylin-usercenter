package com.rongcapital.usercenter.api.service;

import com.rongcapital.usercenter.api.exception.RegiesterException;
import com.rongcapital.usercenter.api.vo.Base64VcodeResponse;
import com.rongcapital.usercenter.api.vo.BaseResultVo;
import com.rongcapital.usercenter.api.vo.LoginInfo;
import com.rongcapital.usercenter.api.vo.LoginResultVo;
import com.rongcapital.usercenter.api.vo.ResponseResultVo;
import com.rongcapital.usercenter.api.vo.UserRegiesterInfo;
import com.rongcapital.usercenter.api.vo.WeChatRegisterInfo;

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
    public BaseResultVo<ResponseResultVo> regiest(UserRegiesterInfo regiesterInfo) throws RegiesterException;
  
    /**
     * 
     * Discription: 微信注册
     * @param weChatRegisterInfo
     * @return
     * @throws RegiesterException
     *  BaseResultVo<ResponseResultVo>
     * @author bihf
     * @since 2017年1月4日
     */
    public BaseResultVo<ResponseResultVo> weChatRegist(WeChatRegisterInfo weChatRegisterInfo) throws RegiesterException;
    
    /**
     * Discription:登录
     * @param loginInfo 登录信息
     * @return BaseResultVo<String>
     * @author LCER
     * @since 2016年10月21日
     */
    public LoginResultVo<ResponseResultVo> login(LoginInfo loginInfo);
    
    
    
    /**
     * Discription:获取验证码
     * @param appCode
     * @param xAuthToken
     * @return BaseResultVo<Base64VcodeResponse>
     * @author LCER
     * @since 2016年10月28日
     */
    public BaseResultVo<Base64VcodeResponse> getBase64Vcode(String appCode, String xAuthToken);
    
    /**
     * 
     * Discription:注册，单独为商户平台使用，注册时使用模拟机构。同时用户中心保存实际用户在机构映射表中
     * @param regiesterInfo  
     * @param actualOrgCode  实际机构号
     * @param mechantName 所属商户登陆名   ，主要记录商户平台下属 个人或者企业开户，保存对应关系
     * @return
     * @throws RegiesterException
     *  BaseResultVo<ResponseResultVo>
     * @author bihf
     * @since 2017年2月22日
     */
    public BaseResultVo<ResponseResultVo> regiestMerchant(UserRegiesterInfo regiesterInfo,String actualOrgCode,String mechantName) throws RegiesterException;

}
