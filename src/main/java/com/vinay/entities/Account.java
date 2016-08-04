package com.vinay.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import com.vinay.model.SubscriptionEvent;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String uuid;

	@Column(name="edition_code")
	private String editionCode;

	@Column(name="item_quentity")
	private BigDecimal itemQuentity;

	@Column(name="item_unit")
	private String itemUnit;

	@Column(name="marketplace_baseurl")
	private String marketplaceBaseurl;

	@Column(name="marketplace_parter")
	private String marketplaceParter;

	@Column(name="pricing_duration")
	private String pricingDuration;

	@Column(name="subscription_status")
	private String subscriptionStatus;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="created_by")
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="last_updated_by")
	private User user2;

	//bi-directional many-to-many association to User
	@ManyToMany
	@JoinTable(
		name="account_user"
		, joinColumns={
			@JoinColumn(name="account_uuid")
			}
		, inverseJoinColumns={
			@JoinColumn(name="user_uuid")
			}
		)
	private List<User> users;

	public Account() {
	}

	public Account(SubscriptionEvent event, Company company, User user) {
		this.uuid = UUID.randomUUID().toString();
		this.editionCode = event.getPayload().getOrder().getEditionCode();
		this.pricingDuration = event.getPayload().getOrder().getPricingDuration();
		if (event.getPayload().getOrder().getItems() != null && event.getPayload().getOrder().getItems().length > 0) {
			this.itemQuentity = event.getPayload().getOrder().getItems()[0].getQuantity();
			this.itemUnit = event.getPayload().getOrder().getItems()[0].getUnit();
		}
		
		this.marketplaceParter = event.getMarketplace().getPartner();
		this.marketplaceBaseurl = event.getMarketplace().getBaseUrl();
		this.setSubscriptionStatus("ACTIVE");
		this.company = company;
		this.user1 = user;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getEditionCode() {
		return this.editionCode;
	}

	public void setEditionCode(String editionCode) {
		this.editionCode = editionCode;
	}

	public BigDecimal getItemQuentity() {
		return this.itemQuentity;
	}

	public void setItemQuentity(BigDecimal itemQuentity) {
		this.itemQuentity = itemQuentity;
	}

	public String getItemUnit() {
		return this.itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getMarketplaceBaseurl() {
		return this.marketplaceBaseurl;
	}

	public void setMarketplaceBaseurl(String marketplaceBaseurl) {
		this.marketplaceBaseurl = marketplaceBaseurl;
	}

	public String getMarketplaceParter() {
		return this.marketplaceParter;
	}

	public void setMarketplaceParter(String marketplaceParter) {
		this.marketplaceParter = marketplaceParter;
	}

	public String getPricingDuration() {
		return this.pricingDuration;
	}

	public void setPricingDuration(String pricingDuration) {
		this.pricingDuration = pricingDuration;
	}

	public String getSubscriptionStatus() {
		return this.subscriptionStatus;
	}

	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Account [uuid=" + uuid + ", editionCode=" + editionCode + ", itemQuentity=" + itemQuentity
				+ ", itemUnit=" + itemUnit + ", marketplaceBaseurl=" + marketplaceBaseurl + ", marketplaceParter="
				+ marketplaceParter + ", pricingDuration=" + pricingDuration + ", subscriptionStatus="
				+ subscriptionStatus + "]";
	}
	
	

}