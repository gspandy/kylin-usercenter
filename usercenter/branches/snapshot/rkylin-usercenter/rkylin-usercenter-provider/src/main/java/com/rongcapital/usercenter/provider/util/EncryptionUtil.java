package com.rongcapital.usercenter.provider.util;

import java.io.UnsupportedEncodingException;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密 Description:
 * 
 * @author: libi
 * @CreateDate: 2016年8月18日
 * @version: V1.0
 */
public class EncryptionUtil {

    public static String md5Encry(String password) {
        return Hashing.md5().newHasher().putString(password, Charsets.UTF_8).hash().toString();
    }

    public static String md5Encry(String password, String slat) {
        return Hashing.md5().newHasher().putString(password, Charsets.UTF_8).putString(slat, Charsets.UTF_8).hash()
                .toString();
    }

    public static String sha256Encry(String password) {

        return Hashing.sha256().newHasher().putString(password, Charsets.UTF_8).hash().toString();
    }

    public static String sha256Encry(String password, String slat) {

        return Hashing.sha256().newHasher().putString(password, Charsets.UTF_8).putString(slat, Charsets.UTF_8).hash()
                .toString();
    }

    public static boolean validate(String password, String slat, String sha256Pwd) {
        String confirm = sha256Encry(password, slat);
        if (confirm.equals(sha256Pwd)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String encodeBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
  
    // 解密  
    public static String decodeBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
    
    //md5: a7a6eda7d5bf09f6a8f21dd1aa38ee0b
    //sale: sJt4ChymaV53LIaduKqEnU+wxEw=

    //7d2cadab727abbd37a912febfe97ba9cdcfe97d7a9561bbef1493506607810e5
    public static void main(String[] args) {
        try {
            
            System.out.println(sha256Encry("123456", "sJt4ChymaV53LIaduKqEnU+wxEw="));
        } catch (Exception e) {

        }
    }
}
