package com.pac.msm.component.domain;

public class Error extends Metadata {
	
	private String errorType;
	private int errorCode;
	private String developerMessage;
	
	public Error(String errorType, int errorCode, String developerMessage) {
		super();
		this.errorType = errorType;
		this.errorCode = errorCode;
		this.developerMessage = developerMessage;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
	
}
