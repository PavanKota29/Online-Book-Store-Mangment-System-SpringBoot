package com.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.entity.Author;


@Repository
public interface AuthorRepo extends JpaRepository<Author, Long>{
	@Query(value="delete from author where aid=?1",nativeQuery=true)
	public boolean deleteId(long aid);
}
