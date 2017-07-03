package com.rongcapital.usercenter.provider.util;

import java.security.SecureRandom;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 生成随机数（盐值,tonken) Description:
 * 
 * @author: libi
 * @CreateDate: 2016年8月18日
 * @version: V1.0
 */
public class SaltUtil {

    public static final int SALT_LENGTH = 20;

    public static byte[] nextSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);
        return salt;
    }

    public static String createSalt() throws Exception {
        byte[] saltBytes = nextSalt();
        return encode(saltBytes);

    }

    public static String encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    public static byte[] decode(String string) {
        try {
            return new BASE64Decoder().decodeBuffer(string);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(createSalt());
        System.out.println(SaltUtil.createSalt());
        
    }
}
