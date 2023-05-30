package com.blog.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.repository.UserRepo;

@Service
public class CustomerUserDetailService implements UserDetailsService{

	
	@Autowired
	private UserRepo  userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Loading user from database by username
		
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "Useremail"+username, 0));
		
		return user;
	}

}
