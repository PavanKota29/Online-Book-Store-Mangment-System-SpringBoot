package com.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.entity.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
	
	public Book findByBname(String bname);
}
