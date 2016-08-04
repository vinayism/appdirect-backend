package com.vinay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vinay.dao.AccountDao;
import com.vinay.dao.CompanyDao;
import com.vinay.dao.UserDao;
import com.vinay.entities.Account;
import com.vinay.entities.Company;
import com.vinay.entities.User;

@RestController
public class TestController {
	
	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "test/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUsers() {
		List<User> users = userDao.findAll();
		if (users != null) {
			return users.toString();
		} else {
			return "No Users";
		}
	}
	
	@RequestMapping(value = "test/companies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getCompanies() {
		List<Company> companies = companyDao.findAll();
		if (companies != null) {
			return companies.toString();
		} else {
			return "No Companies";
		}
	}
	
	@RequestMapping(value = "test/accounts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAccounts() {
		List<Account> accounts = accountDao.findAll();
		if (accounts != null) {
			return accounts.toString();
		} else {
			return "No Accounts";
		}
	}

}
