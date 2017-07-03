package com.ropapi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class RSATester {

    static String publicKey;
    static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            System.err.println("公钥:\n" + publicKey);
            System.err.println("私钥:\n" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws Exception {

//    	testFile();
        test();
        testSign();
    }
    
    private static void testFile() throws Exception {
    	File inputFile = new File("d:/input.rar");
    	File tmpFile = new File("d:/temp.rar");
    	File outputFile = new File("d:/output.rar");
        byte[] data = readFromFile(inputFile);
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        writeToFile(encodedData, tmpFile);
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        writeToFile(decodedData, outputFile);
    }
	
	public static byte[] readFromFile(File fileName) throws Exception {

		FileInputStream fis = new FileInputStream(fileName);
		byte[] buf = new byte[(int) fileName.length()];
		fis.read(buf);
		fis.close();
		
		return buf;
	}
	
	public static void writeToFile(byte[] content, File fileName) {
		//创建输出流  
        FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(fileName);
	        //写入数据  
	        outStream.write(content);  
	        //关闭输出流  
	        outStream.close();  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

	static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }
}
