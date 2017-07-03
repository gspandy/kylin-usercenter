package com.ropapi.constants;

public class ErrorCodeConstants {

	/** 系统级错误码 */
	// S0：服务请求失败
	public static final String SYS_ERROR_CODE_S0 = "S0";
	// S1：没有传递任何参数
	public static final String SYS_ERROR_CODE_S1 = "S1";
	// S2：没有传递参数:app_key
	public static final String SYS_ERROR_CODE_S2 = "S2";
	// S3：app_key不存在
	public static final String SYS_ERROR_CODE_S3 = "S3";
	// S4：没有传递参数:method
	public static final String SYS_ERROR_CODE_S4 = "S4";
	// S5：没有传递参数:format
	public static final String SYS_ERROR_CODE_S5 = "S5";
	// S6：没有传递参数:session
	public static final String SYS_ERROR_CODE_S6 = "S6";
	// S7：没有传递参数:sign
	public static final String SYS_ERROR_CODE_S7 = "S7";
	// S8：没有传递参数:timestamp
	public static final String SYS_ERROR_CODE_S8 = "S8";
	// S9：session无效
	public static final String SYS_ERROR_CODE_S9 = "S9";
	// S10：timestamp无效
	public static final String SYS_ERROR_CODE_S10 = "S10";
	// S11：sign无效
	public static final String SYS_ERROR_CODE_S11 = "S11";
	// S12：该IP地址无访问权限，请联系相关负责人
	public static final String SYS_ERROR_CODE_S12 = "S12";
	// S13：API没有调用的权限
	public static final String SYS_ERROR_CODE_S13 = "S13";
	
    /**
     * 用户名已存在 
     */
    public static final String USERCENTER_EXSIT_USERNAME = "E0013";
	
	/** 业务级错误码 */

}
