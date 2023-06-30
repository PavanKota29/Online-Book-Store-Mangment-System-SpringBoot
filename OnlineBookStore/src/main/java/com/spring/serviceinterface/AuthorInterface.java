package com.spring.serviceinterface;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.spring.dtoentity.AuthorDTO;



public interface AuthorInterface {
	ResponseEntity<List<AuthorDTO>> getAllAuthor();
	ResponseEntity<AuthorDTO> getAuthor(Long aid);
	ResponseEntity<String> addAuthor(AuthorDTO author);
	ResponseEntity<String> updateAuthor(Long aid,AuthorDTO author);
	ResponseEntity<String> deleteAuthor(Long aid);
}
