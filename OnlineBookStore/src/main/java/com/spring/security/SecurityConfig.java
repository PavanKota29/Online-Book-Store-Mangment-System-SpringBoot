package com.spring.security;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig  {

	@Autowired
	JwtFilter jwt;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	@Primary
	public UserDetailsService userDetailsService() {
		return new CustomUserDetails();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity hs) throws Exception{
		hs.csrf().disable()
		.authorizeHttpRequests()
		.antMatchers("/home/reg","/home/gettoken")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and().exceptionHandling()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		hs.authenticationProvider(daoAuthenticationProvider());
		hs.addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
		return hs.build();
		
	}
		

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider d=new DaoAuthenticationProvider();
		d.setUserDetailsService(userDetailsService());
		d.setPasswordEncoder(passwordEncoder());
		return d;
	}
	
	
}
