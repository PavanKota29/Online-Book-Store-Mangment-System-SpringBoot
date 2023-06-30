package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.entity.Users;
import com.spring.repo.UserRepo;
import com.spring.serviceinterface.UserInterface;

@Service
public class UserServices implements UserInterface{
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public ResponseEntity<String> registerUser(Users user) {
		
		try {
			Users u1=new Users();
			u1.setUname(user.getUname());
			u1.setUphone(user.getUphone());
			u1.setEmail(user.getEmail());
			u1.setPassword(passwordEncoder().encode(user.getPassword()));
			userRepo.save(u1);
			return ResponseEntity.ok("inserted");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<String> updateUser(Long uid, Users user) {
		
		try {
			Users u1=userRepo.findById(uid).get();
			u1.setUname(user.getUname());
			u1.setUphone(user.getUphone());
			u1.setEmail(user.getEmail());
			u1.setPassword(passwordEncoder().encode(user.getPassword()));
			userRepo.save(u1);
			return ResponseEntity.ok("update");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@Autowired
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
