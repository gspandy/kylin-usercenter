package com.ropapi.response;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public abstract class Response {

	@XStreamOmitField
	private boolean callResult = true;

	public boolean getCallResult() {
		return callResult;
	}

	public void setCallResult(boolean callResult) {
		this.callResult = callResult;
	}

}
