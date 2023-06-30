package com.spring.serviceinterface;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.spring.entity.BuyBook;

public interface BuyBookInterface{
	
	public ResponseEntity<String> addBuyBook(BuyBook book);
	public ResponseEntity<String> removeBuyBook(BuyBook book);
	public ResponseEntity<List<BuyBook>> getAllBuyBook(String email);
}
