package com.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.UsernamePass;
import com.spring.entity.Users;
import com.spring.security.CustomUserDetails;
import com.spring.security.JwtUtil;
import com.spring.service.UserServices;

@RestController
@RequestMapping("/home")
public class MainController {
	
	@Autowired
	private  DaoAuthenticationProvider aut;
	
	@Autowired
	private CustomUserDetails custom;
	
	@Autowired
	private JwtUtil jwt;
	
	@Autowired
	private UserServices userService;

	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public ResponseEntity<String> adminLogin() {
		return ResponseEntity.ok("Admin Login");
	}
	
	@PreAuthorize("hasRole('NORMAL')")
	@GetMapping("/user")
	public ResponseEntity<String> userLogin() {
		return ResponseEntity.ok("User Login");
	}
	
	
	@PostMapping("/reg")
	public ResponseEntity<String> userReg(@RequestBody Users user){
		return userService.registerUser(user);
	}	
	
	
	@PostMapping("/gettoken")
	public ResponseEntity<String> getToken(@RequestBody UsernamePass up)throws Exception{	
		
		try {
			this.aut.authenticate(new UsernamePasswordAuthenticationToken(up.getEmail(), up.getPwd()));
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new BadCredentialsException(up.getEmail());
		}
		
		UserDetails usr=this.custom.loadUserByUsername(up.getEmail());
		
		
		return ResponseEntity.ok(jwt.generateToken(usr));
	}
}
