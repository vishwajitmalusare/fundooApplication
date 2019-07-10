package com.bridgelabz.fundoonotes.response;

public class ResponseToken {

	private String statusMessage;
	private int statusCode;
	private String token;

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "ResponseToken [statusMessage=" + statusMessage + ", statusCode=" + statusCode + ", token=" + token
				+ "]";
	}

}