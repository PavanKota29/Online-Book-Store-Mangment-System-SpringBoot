package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.BuyBook;
import com.spring.entity.Users;

import com.spring.security.JwtUtil;
import com.spring.service.BuyBookService;
import com.spring.service.UserServices;


@RestController
@PreAuthorize("hasRole('NORMAL')")
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private JwtUtil jwt;
	
	@Autowired
	private UserServices userService;
	
	@Autowired
	private BuyBookService buy;
	
	@PutMapping("/updateuser/{uid}")
	public ResponseEntity<String> userupdate(@PathVariable("uid") Long uid,@RequestBody Users user){
		return userService.updateUser(uid,user);
	}
	
	@PostMapping("/buyBook")
	public ResponseEntity<String> buybook(@RequestBody BuyBook book,@RequestHeader("Authorization") String token){
		String email=jwt.extractUsername(token.substring(7));
		System.out.println(email);
		book.setEmail(email);
		return buy.addBuyBook(book);
	}
	
	
	@DeleteMapping("/deleteBuyBook")
	public ResponseEntity<String> deleteBuyBook(@RequestBody BuyBook book,@RequestHeader("Authorization") String token){
		String email=jwt.extractUsername(token.substring(7));
		book.setEmail(email);
		return buy.removeBuyBook(book);
	}
	
	@GetMapping("/getAllBuyBook")
	public ResponseEntity<List<BuyBook>> getAllBuyBooks(@RequestHeader("Authorization") String token){
		String email=jwt.extractUsername(token.substring(7));
		
		return buy.getAllBuyBook(email);
	}
	
	
}
