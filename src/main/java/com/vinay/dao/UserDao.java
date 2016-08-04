package com.vinay.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinay.entities.User;

public interface UserDao extends JpaRepository<User, String>{

}
