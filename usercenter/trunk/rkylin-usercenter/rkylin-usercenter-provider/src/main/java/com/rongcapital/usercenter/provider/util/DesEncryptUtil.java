package com.rongcapital.usercenter.provider.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang.StringUtils;


/**
 * android加密解密类
 * 
 * @author Administrator
 * 
 */
public class DesEncryptUtil {

    /** 
     * 密钥算法 
     * */  
    public static final String KEY_ALGORITHM="desede";  
      
    /** 
     * 加密/解密算法/工作模式/填充方式 
     * */  
    public static final String CIPHER_ALGORITHM="desede/CBC/PKCS5Padding";  
    
    /**
     *  密钥 长度不得小于24
     */
	private final static String secretKey = "vNfwIys/tom+MOohCAs5u1/bYlXThb";
	
	// 向量 可有可无 终端后台也要约定
	private final static String iv = "tf3w167m";
	
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

	
	
	/**
	 * 3DES加密
	 * 
	 * @param content
	 *            普通文本
	 * @return
	 * @throws Exception
	 */
	public static String des3Encode(String content) throws Exception {
		if (StringUtils.isEmpty(content)) {
			return "";
		} else {

			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(content.getBytes(encoding));
			return URLEncoder.encode(Base64.encode(encryptData), "utf-8");
		}

	}

	/**
	 * 3DES解密
	 * 
	 * @param content
	 *            加密文本
	 * @return
	 * @throws Exception
	 */
	public static String des3Decode(String content) throws Exception {
		if (StringUtils.isEmpty(content)) {
			return "";
		} else {
			// 先URL转
			String decodeString = URLDecoder.decode(content, "utf-8");
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

			byte[] decryptData = cipher.doFinal(Base64.decode(decodeString));

			return new String(decryptData, encoding);
		}
	}

	public static void main(String[] args) throws Exception{
        String a=DesEncryptUtil.des3Encode("aaabbbccc");
        
        System.out.println(a);
        System.out.println(DesEncryptUtil.des3Decode(a));
        
     
    }
}
