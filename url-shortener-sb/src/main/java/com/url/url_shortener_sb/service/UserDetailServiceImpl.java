package com.url.url_shortener_sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.url.url_shortener_sb.Repository.UserRepository;
import com.url.url_shortener_sb.models.User;

import jakarta.transaction.Transactional;

@Component
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("User not found ith username : "+ username));
		return UserDetailsImpl.build(user);
	}

}
