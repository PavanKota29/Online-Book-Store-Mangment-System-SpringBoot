package com.spring.serviceinterface;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.spring.dtoentity.BookDTO;

public interface BookInterface {
	ResponseEntity<List<BookDTO>> getAllBooks();
	ResponseEntity<BookDTO> getBooks(Long bid);
	ResponseEntity<String> addBooks(BookDTO book);
	ResponseEntity<String> updateBooks(Long bid,BookDTO book);
	ResponseEntity<String> deleteBooks(Long bid);
}
