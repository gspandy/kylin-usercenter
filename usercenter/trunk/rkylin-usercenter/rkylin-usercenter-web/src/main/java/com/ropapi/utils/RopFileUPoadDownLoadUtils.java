package com.ropapi.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Rop.api.ApiException;
import com.Rop.api.DefaultRopClient;
import com.Rop.api.RopClient;
import com.Rop.api.domain.FileUrl;
import com.Rop.api.request.FsFileUploadRequest;
import com.Rop.api.request.FsFileurlGetRequest;
import com.Rop.api.request.FsUrlkeyGetRequest;
import com.Rop.api.response.FsFileUploadResponse;
import com.Rop.api.response.FsFileurlGetResponse;
import com.Rop.api.response.FsUrlkeyGetResponse;

public class RopFileUPoadDownLoadUtils {
    
    private static Logger logger = LoggerFactory.getLogger(RopFileUPoadDownLoadUtils.class);
	
	private static final String UPLOADANDDOWNLOADAPPKEY ;
	private static final String UPLOADANDDOWNLOADAPPSECRET ;
	private static final String UPLOADANDDOWNLOADAPPURL ;
	
	
	
	static{
	    ResourceBundle bundle = ResourceBundle.getBundle("sealAndSignatureConf");
	    UPLOADANDDOWNLOADAPPKEY = bundle.getString("cfca.account.appkey");
	    UPLOADANDDOWNLOADAPPSECRET = bundle.getString("cfca.account.appsecret");
	    UPLOADANDDOWNLOADAPPURL = bundle.getString("cfca.account.appurl");
	}
	
    private static final int DOWNLOAD_CACHESIZE = 1024*4;
	private static RopClient uploadAndDownloadRopClient = new DefaultRopClient(UPLOADANDDOWNLOADAPPURL,UPLOADANDDOWNLOADAPPKEY, UPLOADANDDOWNLOADAPPSECRET);
	private static SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 
	 * Discription:
	 * @param type  文件类型 23
	 * @param invoiceDate
	 * @param batch 批次号，不能重复
	 * @param filePath 文件路径
	 * @return String
	 * @author libi
	 * @throws ParseException 
	 * @since 2016年1月22日
	 */
	public static String upload(int  type,Date invoiceDate,String batch,String filePath) throws ApiException, ParseException{
		FsFileUploadRequest request = new FsFileUploadRequest();
		
		
		request.setType(type);
		if(null != invoiceDate){
			try {
				invoiceDate = formate.parse(formate.format(invoiceDate));
			} catch (ParseException e) {
				logger.error(e.getMessage(),e);
				throw e;
			}
			request.setInvoice_date(invoiceDate);
		}
		request.setBatch(batch);
		request.setPath(filePath);
		logger.debug("文件类型:"+type+"\t filePath:"+filePath);
		FsFileUploadResponse response = ROPClientUtils.execute(uploadAndDownloadRopClient, request);
		logger.debug(response.getBody());
		if(response.isSuccess()){
		    return response.getUrl_key();
		}		else{
		    logger.error(response.getErrorCode()+"\t:"+response.getMsg());
			return null;
		}
	}
	
	public static void main(String[] args) throws ApiException, ParseException {
	    //b2bd6cef-ce06-4487-8db0-0eae137421b2
//		System.out.println(upload(24, new Date(),UUID.randomUUID().toString() , "d://01.pdf"));;
		
		System.out.println(getDownLoadInfo(24,new Date(),"abc" , true));;
	    
//	    System.out.println(getDownLoadUrl("5f238c26-881c-4cf3-bf71-62e905be7edb"));
	}
	
	public static String getDownLoadInfo(int  type,Date invoiceDate,String batch,boolean isGetUrl) throws ApiException, ParseException{
		FsUrlkeyGetRequest request = new FsUrlkeyGetRequest();
		request.setType(type);
		if(null != invoiceDate){
			try {
				invoiceDate = formate.parse(formate.format(invoiceDate));
			} catch (ParseException e) {
				logger.error(e.getMessage());
				throw e;
			}
			request.setInvoice_date(invoiceDate);
		}
		request.setBatch(batch);
		FsUrlkeyGetResponse response = ROPClientUtils.execute(uploadAndDownloadRopClient, request);
		 List<FileUrl> fileurls = response.getFileurls();
		if(null == fileurls || fileurls.size() == 0){
			return null;
		}
		String url_key = fileurls.get(0).getUrl_key();
		if(StringUtils.isNotBlank(url_key)){
			if(isGetUrl){
				FsFileurlGetRequest urlRequest = new FsFileurlGetRequest();
				urlRequest.setUrl_key(url_key);
				FsFileurlGetResponse urlResponse = ROPClientUtils.execute(uploadAndDownloadRopClient, urlRequest);
				return urlResponse.getFile_url();
			}else{
				return url_key;
			}
		}else{
		    logger.error(response.getErrorCode()+"\t:"+response.getMsg());
			return null;
		}
		
	}
	/**
	 * 
	 * Discription:
	 * @param url_key 签完章后的pdf urlkey
	 * @return String
	 * @author libi
	 * @since 2016年1月22日
	 */
	public static String getDownLoadUrl(String url_key) throws ApiException{
		FsFileurlGetRequest urlRequest = new FsFileurlGetRequest();
		urlRequest.setUrl_key(url_key);
		FsFileurlGetResponse urlResponse = ROPClientUtils.execute(uploadAndDownloadRopClient, urlRequest);
		
		String file_url = urlResponse.getFile_url();
		boolean blank = StringUtils.isBlank(file_url);
		if(blank){
		    logger.error(urlResponse.getErrorCode()+"\t:"+urlResponse.getMsg());
		}
		
		logger.debug(file_url);
		return blank?null:file_url;
	}
	
	public static byte[] downLoadFile(int  type,Date invoiceDate,String batch) throws ApiException, ParseException, IOException{
		String downLoadURL = getDownLoadInfo(type, invoiceDate, batch, true);
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
			logger.error(e.getMessage(),e);
			throw e;
		} catch (IOException e) {
		    logger.error(e.getMessage(),e);
			throw e;
		}
		return null;
	}
	public static byte[] downLoadFile(String urlKey) throws ApiException, IOException{
		String downLoadURL = getDownLoadUrl(urlKey);
		if(null == downLoadURL){
		    return null;
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(downLoadURL);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(null != entity){
				InputStream is = entity.getContent();
				if(null != is && is.available() > 0){
				    ByteArrayOutputStream bos = new ByteArrayOutputStream(is.available());
				    byte[] bs = new byte[DOWNLOAD_CACHESIZE];
				    int length = -1;
				    while( (length = is.read(bs)) != -1){
				        bos.write(bs, 0, length);
				    }
				    bos.flush();
				    is.close();
				    return bos.toByteArray();
				}
			}
		} catch (ClientProtocolException e) {
		    logger.error(e.getMessage(),e);
			throw e;
		} catch (IOException e) {
		    logger.error(e.getMessage(),e);
			throw e;
		}
		return null;
	}
	
	/*@Test
	public void getSignal() throws Exception{
		byte[] downLoadFile = downLoadFile("4824ae90-848b-49fd-abda-19a588ec0552");
		System.out.println(new String(downLoadFile));
		FileOutputStream fos = new FileOutputStream("d:/sign.pdf");
		fos.write(downLoadFile);
		fos.flush();
		fos.close();
		
	}*/
}
