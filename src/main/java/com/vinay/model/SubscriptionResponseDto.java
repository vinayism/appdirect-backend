package com.vinay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionResponseDto {
	
	@JsonProperty("success")
	private Boolean success;
	
	@JsonProperty("accountIdentifier")
	private String accountIdentifier;
	
	@JsonProperty("errorCode")
	private String errorCode;
	
	@JsonProperty("message")
	private String message;
	
	public SubscriptionResponseDto(Boolean success) {
		this.success = success;
	}

	public SubscriptionResponseDto(Boolean success, String accountIdentifier) {
		super();
		this.success = success;
		this.accountIdentifier = accountIdentifier;
	}

	public SubscriptionResponseDto(Boolean success, String errorCode, String message) {
		super();
		this.success = success;
		this.errorCode = errorCode;
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
