package com.rkylin.usercenter.util;

import java.util.List;

import com.rkylin.usercenter.util.vo.LoginResponse;
import com.rkylin.usercenter.util.vo.RegiesterResponse;
import com.rongcapital.usercenter.api.po.ExtendUserInfo;
import com.rongcapital.usercenter.api.vo.AuthResultVo;
import com.rongcapital.usercenter.api.vo.Base64VcodeResponse;
import com.rongcapital.usercenter.api.vo.BaseResultVo;

public interface CaasUserServiceProxy {
    
    
    
    
    BaseResultVo<LoginResponse> login(String confFilePath, String appKey, String loginName, String password,
            String vcode, String xAuthToken);
    
    
    BaseResultVo<RegiesterResponse> regiest(String confFilePath, String appKey, String loginName, String password,
            String vcode, String xAuthToken);
    
    BaseResultVo<String> isLoginNameExists(String confFilePath, String appKey, String loginName, String xAuthToken);
    
    /**
     * 
     * Discription:重置密码  ，不需要验证老密码
     * @param userName
     * @param newPwd
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2016年10月10日
     */
    public BaseResultVo<Boolean> resetPwd(String confFilePath,String appKey,String userName,String newPwd);
    /**
     * 
     * Discription:修改密码，需校验老密码
     * @param authCode
     * @param oldPwd
     * @param newPwd
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2016年10月10日
     */
    BaseResultVo<String> updatePwd(String confFilePath, String appKey, String accessToken, String oldPassword,
            String password, String vcode, String xAuthToken);
    /**
     * 
     * Discription:单条验证权限
     * @param accessToken
     * @param resourceCode
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2016年10月10日
     */
    public BaseResultVo<Boolean> checkAuth(String confFilePath,String appKey,String accessToken, String resourceCode,String operationCode);
    /**
     * 
     * Discription:批量验证权限
     * @param accessToken
     * @param resourceCode
     * @param appId
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2016年10月10日
     */
    public BaseResultVo<Boolean> batchCheckAuth(String confFilePath,String appKey,String accessToken, List<String> resourceCode);
    /**
     * 
     * Discription:授权
     * @param authCode
     * @param appCode
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2016年10月10日
     */
    public BaseResultVo<AuthResultVo> userAuth(String confFilePath,String appKey,String authCode);
    /**
     * 
     * Discription:更新token
     * @param refreshToken
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2016年10月10日
     */
    public BaseResultVo<AuthResultVo> refreshToken(String confFilePath,String appKey,String refreshToken);
    /**
     * 
     * Discription:用户退出
     * @param accessToken
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2016年10月10日
     */
    public BaseResultVo<Boolean> logout(String confFilePath,String appKey,String accessToken);
    /**
     * 
     * Discription:
     * @param confFilePath
     * @param accessToken
     * @return
     *  BaseResultVo<PersonInfo>
     * @author bihf
     * @since 2016年10月10日
     */
    public BaseResultVo<ExtendUserInfo> getUserInfo(String confFilePath,String appKey,String accessToken);
    
    /**
     * 
     * Discription:获取用户auth_code
     * @param confFilePath
     * @param accessToken
     * @return
     *  BaseResultVo<String>
     * @author bihf
     * @since 2016年10月11日
     */
    public BaseResultVo<String>   getAuthCode(String confFilePath,String appKey,String accessToken);
    
    
    public BaseResultVo<Base64VcodeResponse>   getBase64Vcode(String confFilePath,String appKey,String xAuthToken);
    
    





   
   
}
