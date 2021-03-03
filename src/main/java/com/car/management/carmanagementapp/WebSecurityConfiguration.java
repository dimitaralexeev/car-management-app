/**
 * 
 */
package com.car.management.carmanagementapp;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.car.management.carmanagementapp.service.UserPrincipalDetailsService;

/**
 * @author Dimitar
 *
 */

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private UserPrincipalDetailsService userPrincipalDetailsSerivice;

	public WebSecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsSerivice) {
		this.userPrincipalDetailsSerivice = userPrincipalDetailsSerivice;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		super.configure(auth);

		auth.userDetailsService(userPrincipalDetailsSerivice);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/**").permitAll().requestMatchers(EndpointRequest.toAnyEndpoint())
				.permitAll().and().csrf().disable();
		http.headers().frameOptions().disable();
	}

}
