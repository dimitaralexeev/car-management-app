package com.car.management.carmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.management.carmanagementapp.bean.UserBean;

@Repository
public interface IUserRepository extends JpaRepository<UserBean, Integer> {

	UserBean findUserByUsernameAndPassword(String username, String password);

	UserBean findByUsername(String username);
	
	UserBean findByEmail(String email);
}
