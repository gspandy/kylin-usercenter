package com.rongcapital.usercenter.util;

import com.ropapi.response.ErrorResponse;

public class ParamValidateUtils {

    public static boolean isStrArrayIsEmpty(String[] array){
        if(null == array || 0 == array.length){
            return true;
        }else{
            return false;
        }
    }
    public static  ErrorResponse validateParamIsEmpty(String[] params,String errorCode,String errorMsg){
        if(isStrArrayIsEmpty(params)){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode(errorCode);
            errorResponse.setMsg(errorMsg);
            return errorResponse;
        }else{
            return null;
        }
    }
}
