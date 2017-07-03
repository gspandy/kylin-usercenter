package com.rongcapital.usercenter.api.service;

import com.rongcapital.usercenter.api.po.ExtendUserInfo;
import com.rongcapital.usercenter.api.po.UserInfoPo;
import com.rongcapital.usercenter.api.vo.AuthResultVo;
import com.rongcapital.usercenter.api.vo.BaseResultVo;
import com.rongcapital.usercenter.api.vo.LoginInfo;
import com.rongcapital.usercenter.api.vo.LoginInfo.TerminalType;

public interface UserCenterService {
    
 
    
    public BaseResultVo<String> isLoginNameExists(String loginName,String appCode, String xAuthToken);
    
    /**
     * 
     * Discription: 密码重置
     * @param userInfo  用户信息实体   
     * @param oldPwd  老密码
     * @param newPwd   新密码
     * @param appCode  应用code
     * @return
     *  BaseResultVo<String>
     * @author bihf
     * @since 2016年10月10日
     */
    public BaseResultVo<Boolean>  passwordReset(UserInfoPo userInfo,String newPwd,String appCode);
    /**
     * 密码修改 登录授权后可修改，因为单独拿出处理
     * @param userToken
     * @param oldPwd
     * @param newPwd
     * @param appCode
     * @return  xAuthToken 生成新的token引导登陆使用
     */
    public BaseResultVo<String>  passwordUpdate(String userToken,String oldPwd,String newPwd,String appCode,String vcode,String xAuthToken);
    /**
     * 
     * Discription: 获取用户信息
     * @param userInfo  用户信息实体   
     * @return
     *  BaseResultVo<String>
     * @author bihf
     * @since 2016年10月10日
     */
    public BaseResultVo<ExtendUserInfo>  getUserInfo(String userToken) throws Exception ;
   
    /**
     * 
     * Discription:
     * @param userInfo 用户信息实体   
     * @param appCode  应用code
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2016年10月10日
     */
    public BaseResultVo<Boolean>  checkAuth(String userToken,String appCode,TerminalType terminalType);
    /**
     * 
     * Discription: 用户退出
     * @param userName  用户登录名
     * @param appCode  应用code
     * @return
     *  BaseResultVo<String>
     * @author bihf
     * @since 2016年10月10日
     */
    public BaseResultVo<Boolean>  logout(String userToken,String appCode,TerminalType terminalType);  
 
} 
