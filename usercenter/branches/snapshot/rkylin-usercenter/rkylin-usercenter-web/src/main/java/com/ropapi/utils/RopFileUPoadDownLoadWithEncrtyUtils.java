package com.ropapi.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.Rop.api.DefaultRopClient;
import com.Rop.api.RopClient;
import com.Rop.api.request.FsFileUploadRequest;
import com.Rop.api.request.FsFileurlGetRequest;
import com.Rop.api.response.FsFileUploadResponse;
import com.Rop.api.response.FsFileurlGetResponse;

public class RopFileUPoadDownLoadWithEncrtyUtils {
	
	private static final String UPLOADANDDOWNLOADAPPKEY = "A9E1F858-8231-4366-AAFE-3B78D82F4E2D";
	private static final String UPLOADANDDOWNLOADAPPSECRET = "0892450F-E090-4F8B-B9A2-6F33997ED9E6";
	private static final String UPLOADANDDOWNLOADAPPURL = "https://api.open.ruixuesoft.com:30005/ropapi";
	private static RopClient uploadAndDownloadRopClient = new DefaultRopClient(UPLOADANDDOWNLOADAPPURL,UPLOADANDDOWNLOADAPPKEY, UPLOADANDDOWNLOADAPPSECRET);
	private static SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
	private static final String K2RPUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDc0yzM1CaQBrpslRdptriy2IpmDPOLwP3LeEyk/Ta4aPWy3OIQlBpMJNOho3ynJ8gd4uHBsHkSswV6yr97EMfapGGalmpWHaX4oAyMKbGLFydmbFZB4vxSydt9oDJ1GwP1YqQaBE/cNGtxuAblhlxlkr+PbeaLgQSZ/mKgQXYVYwIDAQAB";
	private static final String R2KPRIVATEKEY ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANRrkstXLzB64d5WGGiSPVp6sbVkpCY9H4uueb5qrq+eUE1lp6qwv5N9ng0c95WmR5dJkKhW2mHwV7C62Ca64hH1h4/TakM4CYht4ZSDmlOFWmqOMhHEV8r9Uyli6zo+i40YNWCNMXuZeVXu1cQjLn/F8QFdShPB0wOq32xACmc7AgMBAAECgYEAtEU+5PZug9QlT4z9VfGVw+2QVwK8E/+Xf/FoPGNJMJ1IRQbaTg+F5eObeEF3FDdzVj8GVvauiqNvp/pJakT7iPpo6xdzrHCXFlEt3Bl5UPwDl2mqHfrkPhWJp0+jyly4UeSOW+rtmEEPB0JQ2Il4OfK0qesu8kNr2vYRJb7NrJECQQDqGI6vLeY6xLUDM+h41blGrDPkKbT/gOpOlKUJmmcdwL7XR7ymqUi3OiYKZVi4tcd98/t52Y8UKDceRYT6ueZNAkEA6EvN6Q7vEDwTiGlvER9gmiUuSlxIYgi4xvAYQAqHxK1kC0IXORcvSmJsw1zc5H0tlaT6LfbqBY/tC6g4FAVXpwJAZCPbLb8BxNQO5u7WnJI4rq3NiOX6gm4gTTszGleNkuG4AZmzbsvtyku6qCnQeTtxukSbp/VUSnglk/KDP0o05QJAJMYzX8zcbb6E/Rhr29MS0PRH4r+/Ob3VurCxthm0qp8kcl/RG1mfQ/BW9YqS8Z0bhVArxvEK1TyWszy7O/goiwJBAIbJ7TkaZK9JWWX1auKb1hf2GBYUwa8jRNKez0Lj/p3EqQnV8ZYPRqhXZzIYcWe2bLY8rZ90FLgV98kGhtw5Hgw=";
	
	
	
	
	public static String upload(String filePath) throws Exception{
		FsFileUploadRequest request = new FsFileUploadRequest();
		String batch = UUID.randomUUID().toString();
		int  type = 23;
		Date invoiceDate = new Date();
		request.setType(type);
		if(null != invoiceDate){
			try {
				invoiceDate = formate.parse(formate.format(invoiceDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			request.setInvoice_date(invoiceDate);
		}
		
		File orgFile = new File(filePath);
		String parent = orgFile.getParent();
		
		byte[] readFileToByteArray = FileUtils.readFileToByteArray(orgFile);
		byte[] encryptByPublicKey = RSAUtils.encryptByPublicKey(readFileToByteArray, K2RPUBLICKEY);
		
		String destPath = parent+File.separator+UUID.randomUUID().toString()+".pdf";
		FileUtils.writeByteArrayToFile(new File(destPath), encryptByPublicKey);
		
		request.setBatch(batch);
		request.setPath(destPath);
		FsFileUploadResponse response = ROPClientUtils.execute(uploadAndDownloadRopClient, request);
		System.out.println(response.getBody());
		String url_key = response.getUrl_key();
		if(StringUtils.isNotBlank(url_key)){
			return url_key;
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
	    //02e7667c-ddad-4ca5-a08d-0e0987376b5f
	    // 1f0dc842-ac7c-4b77-b930-5e4f017aa0e1  1M
	    System.out.println(upload("C:\\Users\\LCER\\Desktop\\kezhan_rsa\\sign_demo\\ori.pdf"));
//	    System.out.println(getDownLoadUrl("e1350da6-50c5-40fb-b96e-d4d951ccf95e"));
	    //c3675773-3eb8-4a2f-8261-ae81f552d735
//	    download("c3675773-3eb8-4a2f-8261-ae81f552d735","d:/222.pdf");
	}
	
	
	public static void download(String url_key,String destFile) throws Exception{
		FsFileurlGetRequest urlRequest = new FsFileurlGetRequest();
		urlRequest.setUrl_key(url_key);
		FsFileurlGetResponse urlResponse = ROPClientUtils.execute(uploadAndDownloadRopClient, urlRequest);
		System.out.println(urlResponse.getFile_url());
		String file_url = urlResponse.getFile_url();
		byte[] downLoadFile = downLoadFile(file_url);
		byte[] decryptByPrivateKey = RSAUtils.decryptByPrivateKey(downLoadFile, R2KPRIVATEKEY);
		File file = new File(destFile);
		if(!file.getParentFile().exists()){
		    file.getParentFile().mkdirs();
		}
		FileUtils.writeByteArrayToFile(file, decryptByPrivateKey);
	
	}
	
	
	private static byte[] downLoadFile(String downLoadURL){
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(downLoadURL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(null != entity){
                InputStream is = entity.getContent();
                ByteArrayOutputStream bos = new ByteArrayOutputStream(is.available());
                byte[] bs = new byte[1024];
                int length = -1;
                while( (length = is.read(bs)) != -1){
                    bos.write(bs, 0, length);
                }
                bos.flush();
                is.close();
                return bos.toByteArray();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	
	
}
