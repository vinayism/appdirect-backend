package com.vinay.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.vinay.model.Creator;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String uuid;

	private String city;

	private String country;

	private String email;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	private String state;

	private String street1;

	private String street2;

	private String zip;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="user1")
	private List<Account> accounts1;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="user2")
	private List<Account> accounts2;

	//bi-directional many-to-many association to Account
	@ManyToMany(mappedBy="users")
	private List<Account> accounts3;

	public User() {
	}

	public User(Creator creator) {
		this.uuid = creator.getUuid();
		this.email = creator.getEmail();
		this.firstName = creator.getFirstName();
		this.lastName = creator.getLastName();
		if (creator.getAddress() != null) {
			this.city = creator.getAddress().getCity();
			this.country = creator.getAddress().getCountry();
			this.state = creator.getAddress().getState();
			this.zip = creator.getAddress().getZip();
			this.street1 = creator.getAddress().getStreet1();
			this.street2 = creator.getAddress().getStreet2();
		}
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet1() {
		return this.street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return this.street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<Account> getAccounts1() {
		return this.accounts1;
	}

	public void setAccounts1(List<Account> accounts1) {
		this.accounts1 = accounts1;
	}

	public Account addAccounts1(Account accounts1) {
		getAccounts1().add(accounts1);
		accounts1.setUser1(this);

		return accounts1;
	}

	public Account removeAccounts1(Account accounts1) {
		getAccounts1().remove(accounts1);
		accounts1.setUser1(null);

		return accounts1;
	}

	public List<Account> getAccounts2() {
		return this.accounts2;
	}

	public void setAccounts2(List<Account> accounts2) {
		this.accounts2 = accounts2;
	}

	public Account addAccounts2(Account accounts2) {
		getAccounts2().add(accounts2);
		accounts2.setUser2(this);

		return accounts2;
	}

	public Account removeAccounts2(Account accounts2) {
		getAccounts2().remove(accounts2);
		accounts2.setUser2(null);

		return accounts2;
	}

	public List<Account> getAccounts3() {
		return this.accounts3;
	}

	public void setAccounts3(List<Account> accounts3) {
		this.accounts3 = accounts3;
	}

	@Override
	public String toString() {
		return "User [uuid=" + uuid + ", city=" + city + ", country=" + country + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", state=" + state + ", street1=" + street1 + ", street2="
				+ street2 + ", zip=" + zip + "]";
	}

	
}