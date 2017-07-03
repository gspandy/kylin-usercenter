package com.ropapi.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LcerTestResponse extends Response implements Serializable {
	
	private List<String> list = new ArrayList<String>();
	
	
	private String content;


	public List<String> getList() {
		return list;
	}


	public void setList(List<String> list) {
		this.list = list;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
	
	

}
