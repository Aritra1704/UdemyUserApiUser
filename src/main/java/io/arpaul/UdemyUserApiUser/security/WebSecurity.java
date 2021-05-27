package io.arpaul.UdemyUserApiUser.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.arpaul.UdemyUserApiUser.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private Environment environment;
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(Environment environment, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.environment = environment;
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("gateway.ip >> "+environment.getProperty("gateway.ip"));
		System.out.println("server.address >> "+environment.getProperty("server.address"));
		System.out.println("server.port >> "+environment.getProperty("server.port"));
		http.csrf().disable();
		http.authorizeRequests()
//		.antMatchers("/**").hasIpAddress(environment.getProperty("server.port"))// Instead of any IP use only gateway IP
//		.and()
//		.addFilter(getAuthenticationFilter());
		.antMatchers("/users/**").permitAll()
		.and()
		.addFilter(getAuthenticationFilter());
		
//		disable for h2 console
//		http.headers().frameOptions().disable();
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter();
		authenticationFilter.setAuthenticationManager(authenticationManager());
		return authenticationFilter;
	}
}
