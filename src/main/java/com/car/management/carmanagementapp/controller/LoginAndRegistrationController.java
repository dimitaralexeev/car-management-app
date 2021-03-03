/**
 * 
 */
package com.car.management.carmanagementapp.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.car.management.carmanagementapp.WebSecurityConfiguration;
import com.car.management.carmanagementapp.bean.UserBean;
import com.car.management.carmanagementapp.repository.IUserRepository;

/**
 * @author Dimitar
 *
 */

@RestController
public class LoginAndRegistrationController {

	private IUserRepository userRepository;

	private WebSecurityConfiguration webSecurityConfiguration;

	public LoginAndRegistrationController(IUserRepository userRepository,
			WebSecurityConfiguration webSecurityConfiguration) {
		this.userRepository = userRepository;
		this.webSecurityConfiguration = webSecurityConfiguration;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @return 
	 */
	@PostMapping(path = "/login")
	public String login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, HttpSession session) {

		UserBean user = userRepository.findUserByUsernameAndPassword(username, hashPassword(password));

		if (user != null) {
			session.setAttribute("user", user);

			try {
				UserDetails userDetails = webSecurityConfiguration.userDetailsServiceBean()
						.loadUserByUsername(user.getUsername());

				if (userDetails != null) {
					Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
							userDetails.getPassword(), userDetails.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(auth);

					ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
							.currentRequestAttributes();

					HttpSession httpSession = attr.getRequest().getSession(true);

					httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
				}

				return "home.html";
			} catch (UsernameNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "Error: Wrong username or password";
	}

	/**
	 * 
	 * @param username
	 * @param email
	 * @param password
	 * @param repeatPassword
	 * @return
	 */
	@PostMapping(path = "/register")
	public String register(@RequestParam(value = "username") String username,
			@RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
			@RequestParam(value = "repeatPassword") String repeatPassword) {

		if (!password.equals(repeatPassword)) {
			return "Error:Not equel passwords";
		}

		if (username == null || username.isEmpty() || password == null || password.isEmpty() || email == null
				|| email.isEmpty()) {
			return "Error:Empty field";
		}

		UserBean user = new UserBean(username, hashPassword(password), email);
		userRepository.saveAndFlush(user);
		return "Account is created";
	}
	
	/**
	 * 
	 * @param session
	 * @return 
	 */
	@PostMapping(path = "/logout")
	public ResponseEntity<Boolean> logout(HttpSession session) {

		UserBean user = (UserBean) session.getAttribute("user");

		if (user != null) {
			session.invalidate();
			return new ResponseEntity<>(true, HttpStatus.OK);
		}

		return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * 
	 * @param password
	 * @return
	 */
	private String hashPassword(String password) {

		StringBuilder result = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(password.getBytes());

			byte[] bytes = md.digest();

			for (int i = 0; i < bytes.length; i++) {
				result.append((char) bytes[i]);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result.toString();
	}
}
