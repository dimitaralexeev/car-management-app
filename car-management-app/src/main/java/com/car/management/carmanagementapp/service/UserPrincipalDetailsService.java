/**
 * 
 */
package com.car.management.carmanagementapp.service;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.car.management.carmanagementapp.UserPrincipal;
import com.car.management.carmanagementapp.bean.RoleBean;
import com.car.management.carmanagementapp.bean.UserBean;
import com.car.management.carmanagementapp.repository.IUserRepository;

/**
 * @author Dimitar
 *
 */

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

	private IUserRepository userRepository;

	public UserPrincipalDetailsService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserBean user = userRepository.findByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException(username);

		Set<RoleBean> roles = user.getRoles();

		return new UserPrincipal(user, roles);
	}

}
