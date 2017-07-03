package com.rongcapital.usercenter.provider.constant;

/**
 * 账户相关常量
 * Description:
 * @author: Achilles
 * @CreateDate: 2016年3月8日
 * @version: V1.0
 */
public class UserInfoConstants {
	
    /**
     * 账户属性:企业
     */
    public static final String  ACCOUNT_PROPERTY_ENTERPRISE= "1";
    /**
     * 账户属性:个人
     */
    public static final String  ACCOUNT_PROPERTY_PERSONAGE= "2";
    /**
     * 账户属性:内部户
     */
    public static final String  ACCOUNT_PROPERTY_INTERIOR= "3";
    /**
     * 证件类型:其他
     */
    public static final String  CERTIFICATE_TYPE_OTHER= "X";
    /**
     * 用户类型:商户
     */
    public static final String  USER_TYPE_MERCHANT= "1";
    /**
     * 用户类型:普通用户
     */
    public static final String  USER_TYPE_DOMESTIC_CONSUMER = "2";
    /**
     * 通联暂不支持的银行
     */
    public static final Integer BANK_TL_NOT_WORK = 0;
    /**
     * 通联支持的银行
     */
    public static final Integer BANK_TL_WORK= 1;
    /**
     * 批量导入对公账户卡上限
     */
    public static final int BATCH_CORPORATE_IMPORT_LIMIT = 50;
    /**
     * 银行账户(卡)信息查询：查询所有卡
     */
    public static final String CARD_QUERY_ALL = "2";
    /**
     * 银行账户(卡)信息查询：查询结算卡
     */
    public static final String CARD_QUERY_DEBIT= "1";
	
    /**
     * 银行账户(卡)信息是否校验：校验
     */
    public static final Integer CARD_CHECKED = 1;
    /**
     * 银行账户(卡)信息是否校验：不校验
     */
    public static final Integer CARD_UNCHECKED= 0;
    
    /**
     * 银行账户(卡)状态：失效
     */
    public static final Integer CARD_STATUS_INVALID = 0;
    /**
     * 银行账户(卡)状态：正常
     */
    public static final Integer CARD_STATUS_NORMAL = 1;
    /**
     * 银行账户(卡)状态：待审核
     */
    public static final Integer CARD_STATUS_AUDIT = 2;
    /**
     * 银行账户(卡)状态：审核中
     */
    public static final Integer CARD_STATUS_AUDITING = 3;
    /**
     * 银行账户(卡)状态：审核失败
     */
    
    public static final Integer CARD_STATUS_FAILURE = 4;
    /**
     * 银行账户(卡)目的：结算卡
     */
    public static final String ACC_PURPOSE_DEBIT_CARD = "1";
    /**
     * 银行账户(卡)目的：其他卡
     */
    public static final String ACC_PURPOSE_OTHER_CARD = "2";
    /**
     * 银行账户(卡)目的：提现卡
     */
    public static final String ACC_PURPOSE_CASH_CARD = "3";
    /**
     * 银行账户(卡)目的：结算提现一体卡
     */
    public static final String ACC_PURPOSE_DEBIT_CASH = "4";
    
    /**
     * 银行账户属性(通联)：对公
     */
    public static final String BANK_ACCOUNT_TYPE_PUB_TL = "1";
    /**
     * 银行账户属性(通联)：对私
     */
    public static final String BANK_ACCOUNT_TYPE_PRI_TL = "0";
    /**
     * 银行账户属性：对公
     */
    public static final String BANK_ACCOUNT_TYPE_PUB = "1";
    /**
     * 银行账户属性：对私
     */
    public static final String BANK_ACCOUNT_TYPE_PRI = "2";
    
	/**
	 * 密码类型：支付
	 */
	public static final String PASSWORD_TYPE_PAY = "P";
	/**
	 * 密码类型：查询
	 */
	public static final String PASSWORD_TYPE_QUERY = "Q";
    
	/**
	 * 操作类型：密码加锁
	 */
	public static final String OPERATE_TYPE_LOCK = "lockup";
	/**
	 * 操作类型：密码解锁
	 */
	public static final String OPERATE_TYPE_UNLOCK = "unlock";
	/**
	 * 操作类型：参数表关于密码参数的标示
	 */
	public static final String PASSWORD_LOCK_PARAM = "PASSWORD_LOCK";
	/**
	 * 操作类型：增加
	 */
	public static final String OPERATE_TYPE_ADD = "insert";
	/**
	 * 操作类型：修改
	 */
	public static final String OPERATE_TYPE_UPDATE = "update";
	 /**
     * 密码状态：失效
     */
    public static final Integer PASSWORD_INVALID = 0;
	/**
	 * 密码状态：正常
	 */
	public static final Integer PASSWORD_OK = 1;

	/**
	 * 密码状态：锁定
	 */
	public static final Integer PASSWORD_LOCK = 2;

	/**
	 * 账户状态：未激活
	 */
	public static final Integer ACCOUNT_INACTIVE = 0;

	/**
	 * 账户状态：正常
	 */
	public static final Integer ACCOUNT_OK = 1;

	/**
	 * 账户状态：冻结
	 */
	public static final Integer ACCOUNT_FREEZE = 2;

	/**
	 * 账户状态：销户
	 */
	public static final Integer ACCOUNT_CLOSE = 3;

	/**
	 * 基本账户类型
	 */
	public static final String ACCOUNT_TYPE_BASE = "10001";

	/**
	 * 子账户类型
	 */
	public static final String ACCOUNT_TYPE_CHILD = "10002";

	/*--------------户口号生成格式--------------*/
	public static final String ACCOUNT_PERSON_ID = "TPA";
	
	public static final String ACCOUNT_COMPANY_ID = "TCA";

	public static final String ACCOUNT_INTERNAL_ID = "TIA";
	
	public static final String ACCOUNT_PERSON_CODE = "SPA";
	
	public static final String ACCOUNT_COMPANY_CODE = "SCA";

	public static final String ACCOUNT_INTERNAL_CODE= "SIA";

	public static final String ACCOUNT_CODE_P="RPA";

	public static final String ACCOUNT_CODE_C="RCA";

	public static final String ACCOUNT_CODE_I="RIA";

	public static final String ACCIYBT_TYPE_10001 = "10001";

	public static final String SUCCESS_OK = "ok";

	public static final String REDIS_KEY_OA = "MTGS.AO.";

	public static final String REDIS_KEY_FA = "MTGS.FA.";

	public static final String REDIS_KEY_IPM = "MTGS.IPM.";

	public static final String ACCIYBT_TYPE_10002 = "10002";

	public static final String ACCIYBT_TYPE_10000 = "10000";
	
	public static final String  REFER_ENTRY_ID = "order.flow";
	
	public static final String NO_FORMAT = "0000";
	
	public static final String response_code_ok = "1";

	public static final String CHECK_YES = "1";

	public static final String CHECK_NO = "0";

	public static final String ACCOUNT_PROPERTY_P = "2";

	public static final String ACCOUNT_PROPERTY_C = "1";

	public static final String ACCOUNT_PROPERTY_I = "3";
	
	public static final String status_ok = "1";
	public static final String status_invalid = "0";
	// 发往会计系统的账务系统的标示
	public static final String sys_flag = "2";
	
	public static final String split_flow = "split.flow";
	
	public static final int  interAccountProperty = 3;
	
	public static final String interRootInstCd = "M00000X";
	
	public static final String  sys_referEntryId_start = "ZW";

	public static final int Rs = 1;//内部户融数机构

	public static final int No_Rs = 0;//内部户非融数机构

	public static final String InternalType_RS = "1";//融数内部户开户操作类型

	public static final String InternalType_NO_RS = "2";//非融数内部户开户操作类型

	public static final String  finAccount_tran_dir = "1";
	
	public static final String  finAccount_tran_dir_inter = "2";

	public static final String map_parameterType = "2";
	
	public static final Integer statusId_ok = 1;
	
	// 拆分规则—交易类型：用户账户转账类型
	public static final String dealType_userTransferType = "1";
	
	// 拆分规则—交易类型：未知流水和金额
	public static final String dealType_unknownFlowAndAmount = "4";
	/**
	 * 去鹏元校验参数表标志
	 */
	public static final String PENGYUAN_VERIFY_INST = "PENGYUAN_VERIFY_INST";
	
	// 会唐
	public static final String ht_org = "M000003";
	// 会场云
	public static final String hcy_org = "M0000031";
	
	public static final String mtkernel_tran_result_success = "WF0000";
	
	public static final String back_tranOrderNo_flow = "back.tranOrderNo.flow";
	
	public static final Integer TRANSORDERINFO_CANCEL_TAG_positive= 0;
	
	public static final Integer TRANSORDERINFO_CANCEL_TAG_negative = 1;
	
	public static final Integer TRANSORDERINFO_CANCEL_TAG_Refund = 2;
	
	public static final Integer transOrder_status_fail = 0;
	
	public static final Integer transOrder_status_succ = 1;
	
	public static final Integer transOrder_status_writeoff = 2;
	
	public static final Integer transOrder_status_refund = 3;
	
	public static final Integer transOrder_status_withdraw = 4;
	
	public static final String accountType_inter = "0";
	
	public static final String accountType_org_place1 = "1";
	
	public static final String accountType_org_place2 = "1";
	
	public static final String recordAccountType_yes = "0";
	
	public static final String recordAccountType_no = "1";
	
	public static final String bothTran_name = "bothTran";
	
	public static final String unfrozonAuth_name = "unfrozonAuth";
	
	public static final String bothSingleTran_name = "bothSingleTran";
	
	public static final String writeOff_name = "writeOff";
	
	
	public static final String mtgs_tranOrderNo_flow = "mtgs.tranOrderNo.flow";
	
	public static final Integer corporatAccountInfo_stt_prereview = 2;
	
	public static final Integer corporatAccountInfo_stt_review = 3;
	
    public static final Integer accountInfo_stt_prereview = 2;
	
	public static final Integer accountInfo_stt_review = 3;
	
	/**
	 * 订单状态(代收付结果返回)：成功
	 */
	public static final Integer TRANSORDER_SUCCESS = 4;
	/**
	 * 订单状态(代收付结果返回)：失败
	 */
	public static final Integer TRANSORDER_FAIL = 5;

	/**
	 * 订单业务类型：绑卡信息表(一分钱代付)
	 */
	public static final String BUSI_TYPE_ID_ACCOUNTINFO = "1";
	/**
	 * 订单业务类型：对公卡表(一分钱代付)
	 */
	public static final String BUSI_TYPE_ID_CORPORATACCOUNTINFO = "2";

}
