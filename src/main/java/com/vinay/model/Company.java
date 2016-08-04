package com.vinay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {
	
	@JsonProperty("country")
	private String country;
	@JsonProperty("name")
	private String name;
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("website")
	private String website;
	@JsonProperty("email")
	private String email;
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return "Company [country=" + country + ", name=" + name + ", uuid=" + uuid + ", website=" + website + ", email="
				+ email + ", phoneNumber=" + phoneNumber + "]";
	}
}
