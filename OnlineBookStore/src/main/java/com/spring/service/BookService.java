package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.dtoconveter.BookDtoConverter;
import com.spring.dtoentity.BookDTO;
import com.spring.entity.Author;
import com.spring.entity.Book;
import com.spring.exception.BookNotFound;
import com.spring.exception.InsertingBookException;
import com.spring.repo.AuthorRepo;
import com.spring.repo.BookRepo;
import com.spring.serviceinterface.BookInterface;

@Service
public class BookService implements BookInterface{
	
	@Autowired
	public BookRepo b;
	
	@Autowired
	public AuthorRepo a;
	
	//Getting All Book Details
	public ResponseEntity<List<BookDTO>> getAllBooks(){
		
		if(b.findAll().size() <=0) {
			throw new BookNotFound();
		}else {
			BookDtoConverter dto=new BookDtoConverter();
			return ResponseEntity.ok(dto.EntitytoListDTO(b.findAll()));
		}
	}
	
	//Getting Book details based on id
	public ResponseEntity<BookDTO> getBooks(Long bid){
		try {
			BookDtoConverter dto=new BookDtoConverter();
			return ResponseEntity.ok(dto.EntitytoDTO(b.findById(bid).get()));
		} catch (Exception e) {
			throw new BookNotFound();
		}
	}
	
	//Inserted Book details in table
	public ResponseEntity<String> addBooks(BookDTO book){
		try {
			Long id=book.getAuthor().getAid();
			Author aut=a.findById(id).get();
			book.setAuthor(aut);
			
			BookDtoConverter dto=new BookDtoConverter();
			b.save(dto.DtotoEntity(book));
			return ResponseEntity.ok("insert");
		}catch(Exception e) {
			throw new InsertingBookException();
		}
	}	
	
	
	//Update the Book Details
	public ResponseEntity<String> updateBooks(Long bid,BookDTO book){
		try {
			Long id=book.getAuthor().getAid();
			Author aut=a.findById(id).get();
			book.setAuthor(aut);
	
			Book b1=b.findById(bid).get();
			b1.setBname(book.getBname());
			b1.setAuthor(book.getAuthor());
			b1.setPrice(book.getPrice());
			
			b.save(b1);
			
			return ResponseEntity.ok("update");
		}catch (Exception e) {
			throw new BookNotFound();
		}
	}
	
	//Delete the Book details 	
	public ResponseEntity<String> deleteBooks(Long bid){
		try {
			Book b1=b.findById(bid).get();
			b1.setAuthor(null);
			b.save(b1);
			b.deleteById(bid);
			return ResponseEntity.ok("delete");
		}catch(Exception e) {
			throw new BookNotFound();
		}
	}
	
	
}
