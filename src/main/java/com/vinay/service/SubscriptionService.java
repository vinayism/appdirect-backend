package com.vinay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.vinay.dao.AccountDao;
import com.vinay.dao.CompanyDao;
import com.vinay.dao.UserDao;
import com.vinay.entities.Account;
import com.vinay.entities.Company;
import com.vinay.entities.User;
import com.vinay.exceptions.AccountAlreadyCancelledException;
import com.vinay.exceptions.AccountAlreadyExistException;
import com.vinay.exceptions.AccountNotFoundException;
import com.vinay.model.SubscriptionEvent;

@Service
public class SubscriptionService {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private UserDao userDao;

	@Transactional
	public String createSubscription(SubscriptionEvent event) throws AccountAlreadyExistException {
		Assert.isTrue(event != null && event.getPayload() != null && event.getPayload().getCompany() != null
				&& event.getPayload().getCompany().getUuid() != null, "Company information not provided.");
		Company company = companyDao.findByUuid(event.getPayload().getCompany().getUuid());
		if (company == null) {
			company = new Company(event.getPayload().getCompany());
			companyDao.save(company);
			User user = new User(event.getCreator());
			userDao.save(user);
			Account account = new Account(event, company, user);
			accountDao.save(account);
			return account.getUuid();
		} else {
			throw new AccountAlreadyExistException();
		}
	}

	@Transactional
	public void changeScbscription(SubscriptionEvent event) throws AccountNotFoundException {
		Assert.isTrue(event.getPayload() != null && event.getPayload().getAccount() != null && event.getPayload().getAccount().getAccountIdentifier() != null, "Account information not provided.");
		Assert.isTrue(event.getPayload().getOrder() != null, "Order details not provided.");
		Assert.isTrue(event.getCreator() != null && event.getCreator() .getUuid() != null , "Creator details not provided.");
		if (accountDao.exists(event.getPayload().getAccount().getAccountIdentifier())) {
			Account account = accountDao.findOne(event.getPayload().getAccount().getAccountIdentifier());
			User creator = account.getUser1();
			if (!event.getCreator() .getUuid().equals(creator.getUuid())) {
				if (userDao.exists(event.getCreator().getUuid())) {
					account.setUser1(userDao.findOne(event.getCreator().getUuid()));
				} else {
					account.setUser1(new User(event.getCreator()));
				}
			}
			account.setEditionCode(event.getPayload().getOrder().getEditionCode());
			account.setPricingDuration(event.getPayload().getOrder().getPricingDuration());
			if (event.getPayload().getOrder().getItems() != null && event.getPayload().getOrder().getItems().length > 0) {
				account.setItemQuentity(event.getPayload().getOrder().getItems()[0].getQuantity());
				account.setItemUnit(event.getPayload().getOrder().getItems()[0].getUnit());
			} else {
				account.setItemQuentity(null);
				account.setItemUnit(null);
			}
			accountDao.save(account);
		} else {
			throw new AccountNotFoundException();
		}
	}

	@Transactional
	public void cancelSubscription(SubscriptionEvent event) throws AccountAlreadyCancelledException, AccountNotFoundException {
		Assert.isTrue(event.getPayload() != null && event.getPayload().getAccount() != null && event.getPayload().getAccount().getAccountIdentifier() != null, "Account information not provided.");
		if (accountDao.exists(event.getPayload().getAccount().getAccountIdentifier())) {
			Account account = accountDao.findOne(event.getPayload().getAccount().getAccountIdentifier());
			if ("CANCELLED".equals(account.getSubscriptionStatus())) {
				throw new AccountAlreadyCancelledException();
			} else {
				account.setSubscriptionStatus("CANCELLED");
				accountDao.save(account);
			}
		} else {
			throw new AccountNotFoundException();
		}

	}

}
