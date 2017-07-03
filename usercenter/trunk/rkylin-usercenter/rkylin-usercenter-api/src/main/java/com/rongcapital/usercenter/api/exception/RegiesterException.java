package com.rongcapital.usercenter.api.exception;

public class RegiesterException extends Exception {

    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    
    private String code;
    
    private String message;
    
    
    public RegiesterException(String code,String message){
        this.code = code;
        this.message = message;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "RegiesterException [code=" + code + ", message=" + message + "]";
    }

    
    
    
}
