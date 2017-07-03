package com.rongcapital.usercenter.api.service;

import com.rongcapital.usercenter.api.po.IdentifyInfoVo;
import com.rongcapital.usercenter.api.po.NologinReturnUserInfo;
import com.rongcapital.usercenter.api.po.UserInfoDetail;
import com.rongcapital.usercenter.api.po.UserInfoPo;
import com.rongcapital.usercenter.api.vo.BaseResultVo;
import com.rongcapital.usercenter.api.vo.BindAccountVo;
import com.rongcapital.usercenter.api.vo.LoginInfo.TerminalType;
import com.rongcapital.usercenter.api.vo.OpenAccountCompany;
import com.rongcapital.usercenter.api.vo.OpenAccountPerson;

public interface UserCenterService {
    
 
    /**
     * 
     * Discription:判断登陆是否存在
     * @param loginName
     * @param appCode
     * @param orgCode
     * @return
     *  BaseResultVo<String>
     * @author bihf
     * @since 2016年12月21日
     */
    public BaseResultVo<Boolean> isLoginNameExists(String loginName,String orgCode,String appCode );
  
    
    /**
     * 
     * Discription:未登陆，判断用户是否实名制
     * @param loginName
     * @param orgCode
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2016年12月20日
     */
    public BaseResultVo<Boolean> isIdentify(String loginName,String orgCode);
   /**
   * 
   * Discription:未登陆，判断用户和证件号码是否匹配
   * @param loginName
   * @param orgCode
   * @param idNumber
   * @return
   *  BaseResultVo<Boolean>
   * @author bihf
   * @since 2016年12月20日
   */
    
    public BaseResultVo<Boolean> isMatchLoginNameAndIdNumber(String loginName,String orgCode,String idNumber);
    
   
    /**
     * 
     * Discription: 实名制接口
     * @param userToken
     * @param idcardNo
     * @return
     *  BaseResultVo<String>
     * @author bihf
     * @since 2016年12月19日
     */
    public BaseResultVo<String> identify(IdentifyInfoVo identifyInfoVo);
   
    
    public BaseResultVo<Boolean> modifyIdentify(IdentifyInfoVo identifyInfoVo);
    
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
    public BaseResultVo<Boolean>  passwordReset(String loginName,String orgCode,String accessTicket,String newPwd,String appCode);
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
    public BaseResultVo<UserInfoDetail>  getUserInfo(String userToken) throws Exception ;
   
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
    public BaseResultVo<Boolean>  checkAuth(String userToken,String userId,String orgCode,String appCode,boolean isreflashToken,TerminalType terminalType);
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
    
    /**
     * 
     * Discription:查询登陆失败次数 ，不区分客户端
     * @param userLoginName
     * @param appCode
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2016年12月19日
     */
    public BaseResultVo<Integer> getErrorLoginTime(String userLoginName,String orgCode);
    /**
     * 
     * Discription:用户未登录获取非敏感信息
     * @param userLoginName
     * @param orgCode
     * @return
     *  BaseResultVo<NologinReturnUserInfo>
     * @author bihf
     * @since 2016年12月22日
     */
    
    public BaseResultVo<NologinReturnUserInfo> getNologinUserInfo(String userLoginName,String orgCode);

    
    /**
     * 
     * Discription:通过登陆名获取授权码，目前用于重置密码前置
     * @param userLoginName
     * @param orgCode
     * @return
     *  BaseResultVo<String>
     * @author bihf
     * @since 2016年12月22日
     */
    public BaseResultVo<String> getAccessTicket(String userLoginName,String orgCode,int expireTime);

    /**
     * 
     * Discription:通过userId获取户口
     * @param userId
     * @param orgCode
     * @return
     *  BaseResultVo<String>
     * @author bihf
     * @since 2017年1月7日
     */
    
    public BaseResultVo<String> getUserCodeByUserId(String userId,String orgCode);
    
    /**
     * 
     * Discription:通过身份证号判断用户是否实名制
     * @param idNumber
     * @param orgCode
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2016年12月20日
     */
    public BaseResultVo<Boolean> isIdentifyByIdNumber(String idNumber,String orgCode);
    /**
     * 
     * Discription:通过登陆名和机构号获取用户信息，主要是userId+userCode<支持商户平台>  
     * @param userLoginName
     * @param orgCode
     * @return
     *  BaseResultVo<UserInfoPo>
     * @author bihf
     * @since 2017年2月20日
     */

    public BaseResultVo<UserInfoPo> getUserInfoByUserNameAndOrgCode(String userLoginName,String orgCode); 
    /**
     * 
     * Discription:个人开户
     * @param openAccountPerson
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2017年4月6日
     */
    public BaseResultVo<Boolean> openAccountPerson(OpenAccountPerson openAccountPerson);
    /**
     * 
     * Discription:企业开户
     * @param openAccountPerson
     * @return
     *  BaseResultVo<Boolean>
     * @author bihf
     * @since 2017年4月6日
     */
    public BaseResultVo<Boolean> openAccountCompany(OpenAccountCompany openAccountCompany);
   /**
    * 
    * Discription:绑卡
    * @param bindAccountVo
    * @return
    *  BaseResultVo<Boolean>
    * @author bihf
    * @since 2017年4月6日
    */
    public BaseResultVo<Boolean> bindCard(BindAccountVo bindAccountVo);
} 
