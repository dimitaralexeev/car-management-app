/**
 * 
 */
package com.car.management.carmanagementapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.management.carmanagementapp.bean.UserBean;
//import com.car.management.carmanagementapp.repository.IUserRepository;

/**
 * @author Dimitar
 *
 */

@RestController
public class UserController {

//	private IUserRepository userRepository;
//
//	public UserController(IUserRepository userRepository) {
//		this.userRepository = userRepository;
//	}

	@GetMapping(path = "/user")
	public ResponseEntity<String> getUsername(HttpSession session) {
		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<>(user.getUsername(), HttpStatus.OK);
	}
}
