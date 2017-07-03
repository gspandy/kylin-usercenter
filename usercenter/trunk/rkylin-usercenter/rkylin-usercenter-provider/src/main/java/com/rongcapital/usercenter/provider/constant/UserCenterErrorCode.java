package com.rongcapital.usercenter.provider.constant;

public class UserCenterErrorCode {
  
    /**
     * Description:传递参数用户实体为空
     */
    public static final String USER_ENTITY_EMPTY = "E0001";
    
    /**
     * Description:原始密码为空
     */
    public static final String USER_OLDPASSWORD_EMPTY = "E0002";
    /**
     * Description:新密码为空
     */
    public static final String USER_NEWPASSWORD_EMPTY = "E0003";
    /**
     * Description:密码类型为空
     */
    public static final String USER_PWDTYPE_EMPTY = "E0004";
    
    /**
     * Description:登陆名为空
     */
    public static final String USER_USERNAME_EMPTY = "E0005";
    
    /**
     * Description:用户token为空
     */
    public static final String USER_TOKEN_EMPTY = "E0006";
    
    /**
     * Description:终端类型为空
     */
    public static final String USER_TERMINALTYPE_ERROR = "E0007";
   
    /**
     * Description:终端类型输入非法  只能为 PC(3), ANDROID(1), ISO(2);
     */
    public static final String USER_TERMINALTYPE_lILLEGAL = "E0008";
    
    /**
     * Description: // 程序执行异常
     */
    public static final String USERCENTER_RUNEXCEPTION_ERROR = "E0009";
    /**
     * 用户为登陆
     */
    public static final String USERCENTER_NOLOGIN_ERROR = "E0010";
    /**
     * 不存在的用户
     */
    public static final String USERCENTER_NOTEXSIT_ERROR = "E0011";
   /**
   * 不存在的应用编码 
   */
    public static final String USERCENTER_NOTEXSIT_APPCODE = "E0012";
    
    /**
     * 用户名已存在 
     */
    public static final String USERCENTER_EXSIT_USERNAME = "E0013";
    /**
     * 授权令牌不匹配 
     */
    public static final String ACCESSTICKET_NOTMATCH_ERROR="E0014";
    /**
     * 机构为空 
     */
    public static final String ORG_EMPTY="E0015";
    /**
     * 授权令牌不为空
     */
    public static final String ACCESSTICKET_EMPTY="E0016";
  /**
   * 应用码为空
   */
    public static final String APPCODE_EMPTY="E0017";
   /**
    * 证件号码为空
    */
    public static final String IDCARDNO_EMPTY="E0018";
  
    public static final String IDCARD_HAS_IDENTIFY="E0019";
    
    
    /**
     * Description://密码校验失败，新旧密码不匹配
     */
    public static final String PWD_CHECK_ERROR="PWD001";
  

    

}
