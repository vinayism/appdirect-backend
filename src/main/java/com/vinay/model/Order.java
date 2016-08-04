package com.vinay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
	
	@JsonProperty("editionCode")
	private String editionCode;
	@JsonProperty("pricingDuration")
	private String pricingDuration;
	@JsonProperty("items")
	private Items[] items;
	
	public String getEditionCode() {
		return editionCode;
	}
	public void setEditionCode(String editionCode) {
		this.editionCode = editionCode;
	}
	public String getPricingDuration() {
		return pricingDuration;
	}
	public void setPricingDuration(String pricingDuration) {
		this.pricingDuration = pricingDuration;
	}
	
	
	public Items[] getItems() {
		return items;
	}
	public void setItems(Items[] items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "Order [editionCode=" + editionCode + ", pricingDuration=" + pricingDuration + ", items=" + items + "]";
	}
	
}
