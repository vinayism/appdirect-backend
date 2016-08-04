package com.vinay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionEvent  {
	
	@JsonProperty("type")
	private String type;
	@JsonProperty("marketplace")
	private Marketplace marketplace;
	@JsonProperty("creator")
	private Creator creator;
	@JsonProperty("payload")
	private Payload payload;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Marketplace getMarketplace() {
		return marketplace;
	}
	public void setMarketplace(Marketplace marketplace) {
		this.marketplace = marketplace;
	}
	public Creator getCreator() {
		return creator;
	}
	public void setCreator(Creator creator) {
		this.creator = creator;
	}
	public Payload getPayload() {
		return payload;
	}
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	@Override
	public String toString() {
		return "SubscriptionEvent [type=" + type + ", marketplace=" + marketplace + ", creator=" + creator
				+ ", payload=" + payload + "]";
	}
	
	
	
	

}
