package com.spring.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.spring.entity.Users;
import com.spring.repo.UserRepo;

@Component
public class CustomUserDetails implements UserDetailsService {
	
	@Autowired
	private UserRepo u;
	
	@Autowired
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

				Users users= this.u.findByEmail(email);
				if(users!=null) {
					UserDetails user=User.withUsername(users.getEmail())
							.password(users.getPassword())
							.roles(users.getRole()).build();
					return user;
				}else {
					throw new UsernameNotFoundException(email);
				}
		
	}
}
