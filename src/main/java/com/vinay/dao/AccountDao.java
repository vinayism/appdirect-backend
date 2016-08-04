package com.vinay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vinay.entities.Account;

public interface AccountDao extends JpaRepository<Account, String>{

	@Query("Select acnt from Account acnt where acnt.company.uuid = ?1")
	Account findByCompanyUuid(String companyUuid);

}
