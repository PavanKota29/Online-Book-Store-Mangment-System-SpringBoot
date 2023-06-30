package com.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Users;

public interface UserRepo extends JpaRepository<Users, Long>{
	
	public Users findByEmail(String email); 
}
