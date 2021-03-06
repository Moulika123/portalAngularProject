package com.niit.portalmiddleware.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity SpringSecurityFilterChain
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests().antMatchers("/user").hasRole("ADMIN")
		.and()
		.formLogin()
		.loginProcessingUrl("/authenticate")
		.successForwardUrl("/success")
		.failureForwardUrl("/failure")
	    .usernameParameter("username")
	    .passwordParameter("password")
	    .permitAll()
		.and()
		.cors().disable();
		
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username,password,status from portal_user where username=?")
		.authoritiesByUsernameQuery("select username,role from portal_user where username=?");
		
	}
	
	
	
	

}
