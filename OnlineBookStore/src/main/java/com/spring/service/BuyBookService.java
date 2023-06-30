package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.entity.BuyBook;
import com.spring.exception.BookNotFound;
import com.spring.repo.BookRepo;
import com.spring.repo.BuyBookRepo;
import com.spring.serviceinterface.BuyBookInterface;

@Service
public class BuyBookService implements BuyBookInterface {

	@Autowired
	private BuyBookRepo repo;
	
	@Autowired
	private BookRepo brepo;
	
	
	@Override
	public ResponseEntity<String> addBuyBook(BuyBook book) {
		try {
			brepo.findByBname(book.getBname());
			int c=repo.countBuyBook(book.getBname(),book.getEmail());
			if(c==0) {
				repo.save(book);
			}else {
				BuyBook bb=repo.findBuyBook(book.getBname(), book.getEmail());
				bb.setBname(book.getBname());
				bb.setEmail(book.getEmail());
				bb.setQuentity(bb.getQuentity()+book.getQuentity());
				repo.save(bb);
			}
			return ResponseEntity.ok("Added BuyBook");
		} catch (Exception e) {
			throw new BookNotFound();
		}
	}


	@Override
	public ResponseEntity<String> removeBuyBook(BuyBook book) {
		try {
			BuyBook bb=repo.findBuyBook(book.getBname(), book.getEmail());
			bb.setBname(book.getBname());
			bb.setEmail(book.getEmail());
			if(bb.getQuentity() <book.getQuentity()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quentity Error");
			}else if((bb.getQuentity()-book.getQuentity())==0) {
				repo.deleteById(bb.getId());
				return ResponseEntity.ok("deleted BuyBook");
			}else if(book.getQuentity()==0) {
				bb.setQuentity(1);
			}else {
				bb.setQuentity(bb.getQuentity()-book.getQuentity());
			}
			repo.save(bb);
			return ResponseEntity.ok("deleted BuyBook");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quentity Error");
		}
	}


	@Override
	public ResponseEntity<List<BuyBook>> getAllBuyBook(String email) {
		try {
			List<BuyBook> li=repo.findAllBuyBook(email);
			return ResponseEntity.ok(li);
		} catch (Exception e) {
			throw new BookNotFound();
		}
	}
	
	
	

}
