package com.rongcapital.usercenter.api.vo;

import java.io.Serializable;

public class BaseResultVo<T extends Serializable> implements Serializable {
    
    
    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;

    /**
     * Description: 返回代码
     */
    private String responseCode ; 
    
    /**
     * Description:错误信息 只有responseCode不为200且isSuccess为false设置该值
     */
    private String errorMsg;
    
    /**
     * Description:成功的返回值 ，只有responseCode不为200且isSuccess为true设置该值
     */
    private T resultData;
    
    /**
     * Description:标识返回结果 true为成功，false为失败
     */
    private boolean isSuccess = false;

    

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Override
    public String toString() {
        return "BaseResponse [responseCode=" + responseCode + ", errorMsg=" + errorMsg + ", resultData=" + resultData
                + ", isSuccess=" + isSuccess + "]";
    }
    
    

  
    
    
    
    
    
    

}
