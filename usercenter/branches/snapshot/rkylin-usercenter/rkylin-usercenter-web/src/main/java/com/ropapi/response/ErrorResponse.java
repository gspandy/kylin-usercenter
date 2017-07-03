package com.ropapi.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("error_response")
public class ErrorResponse extends Response{

	// 错误代码
	@XStreamAlias("responsecode")
	private String code;
	// 错误信息
	@XStreamAlias("msg")
	private String msg;
	// 子错误代码
	@XStreamAlias("sub_code")
	private String subCode;
	// 子错误信息
	@XStreamAlias("sub_msg")
	private String subMsg;
	// 参数列表
	@XStreamAlias("args")
	private List<String> args;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getSubMsg() {
		return subMsg;
	}
	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}
	public List<String> getArgs() {
		return args;
	}
	public void setArgs(List<String> args) {
		this.args = args;
	}
	public ErrorResponse(String code, String msg) {
		super();
		super.setCallResult(false);
		this.code = code;
		this.msg = msg;
	}
	public ErrorResponse() {
		super();
	}
	
	
}
