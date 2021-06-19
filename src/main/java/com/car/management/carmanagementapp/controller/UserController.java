/**
 * 
 */
package com.car.management.carmanagementapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.car.management.carmanagementapp.bean.UserBean;
import com.car.management.carmanagementapp.repository.IUserRepository;

/**
 * @author Dimitar
 *
 */

@RestController
public class UserController {

	@Autowired
	private IUserRepository userRepository;

	/**
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping(path = "/user")
	public ResponseEntity<UserBean> getUser(HttpSession session) {
		
		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * 
	 * @param fName
	 * @param lName
	 * @param cityName
	 * @param session
	 * @return
	 */
	@PutMapping(path = "/updateUser")
	public ResponseEntity<Boolean> updateUserInformation(@RequestParam(value = "fName") String fName,
			@RequestParam(value = "lName") String lName, @RequestParam(value = "cityName") String cityName,
			HttpSession session) {
		
		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

		user.setFName(fName);
		user.setLName(lName);
		user.setCityName(cityName);

		user = userRepository.save(user);

		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
