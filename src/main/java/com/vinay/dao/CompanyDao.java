package com.vinay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vinay.entities.Company;

@Repository
public interface CompanyDao extends JpaRepository<Company, String>{

	Company findByUuid(String uuid);

}
