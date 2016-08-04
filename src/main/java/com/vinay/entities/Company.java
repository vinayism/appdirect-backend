package com.vinay.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String uuid;

	private String country;

	private String email;

	private String name;

	@Column(name="phone_number")
	private String phoneNumber;

	private String website;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="company")
	private List<Account> accounts;

	public Company() {
	}
	
	public Company(com.vinay.model.Company companyModel) {
		this.setUuid(companyModel.getUuid());
		this.setName(companyModel.getName());
		this.setEmail(companyModel.getEmail());
		this.setPhoneNumber(companyModel.getPhoneNumber());
		this.setWebsite(companyModel.getWebsite());
		this.setCountry(companyModel.getCountry());
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account addAccount(Account account) {
		getAccounts().add(account);
		account.setCompany(this);

		return account;
	}

	public Account removeAccount(Account account) {
		getAccounts().remove(account);
		account.setCompany(null);

		return account;
	}

	@Override
	public String toString() {
		return "Company [uuid=" + uuid + ", country=" + country + ", email=" + email + ", name=" + name
				+ ", phoneNumber=" + phoneNumber + ", website=" + website + "]";
	}
	
	

}