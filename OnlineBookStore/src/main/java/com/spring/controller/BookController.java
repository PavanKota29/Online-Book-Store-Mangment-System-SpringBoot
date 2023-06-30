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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dtoentity.BookDTO;
import com.spring.service.BookService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	BookService b;
	
	//Controller for Getting book detail
	
	@GetMapping("/getAllBook")
	public ResponseEntity<List<BookDTO>> getAllBooks(){
		return b.getAllBooks();
	}
	
	@GetMapping("/getBook/{bid}")
	public ResponseEntity<BookDTO> getBooks(@PathVariable("bid")Long bid){
		return b.getBooks(bid);
	}
	
	//Controller for Inserted Book Detail in table
	@PostMapping("/addBook")
	public ResponseEntity<String> addBooks(@RequestBody BookDTO book){
		return b.addBooks(book);
	}
	
	//Controller for Update the Book details
	@PutMapping("/updateBook/{bid}")
	public ResponseEntity<String> updateBooks(@PathVariable("bid") Long bid,@RequestBody BookDTO book){
		return b.updateBooks(bid, book);
	}
	
	//Controller for deleting the Book details
	@DeleteMapping("/deleteBook/{bid}")
	public ResponseEntity<String> deleteBooks(@PathVariable("bid") Long bid){
		return b.deleteBooks(bid);
	}



}
