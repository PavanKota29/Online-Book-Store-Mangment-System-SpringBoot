package com.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.entity.BuyBook;

@Repository
public interface BuyBookRepo extends JpaRepository<BuyBook, Long> {
	
	@Query(value = "select count(*) from onlinebookstore.buy_book where bname=?1 and email=?2",nativeQuery = true)
	public int countBuyBook(String bname,String email);
	
	@Query(value = "select * FROM onlinebookstore.buy_book WHERE bname = ?1 AND email = ?2",nativeQuery = true)
	public BuyBook findBuyBook(String bname,String email);
	
	@Query(value = "select * FROM onlinebookstore.buy_book WHERE email = ?1",nativeQuery = true)
	public List<BuyBook> findAllBuyBook(String email);
}
