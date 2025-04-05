package com.url.url_shortener_sb.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.url.url_shortener_sb.Repository.UserRepository;
import com.url.url_shortener_sb.dtos.LoginRequest;
import com.url.url_shortener_sb.models.User;
import com.url.url_shortener_sb.security.jwt.JwtAuthenticationResponse;
import com.url.url_shortener_sb.security.jwt.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private AuthenticationManager authenticationManager;
	private JwtUtils jwtUtils;
	
	public User registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String jwt = jwtUtils.generateToken(userDetails);
		
		return new JwtAuthenticationResponse(jwt) ;
	}
	
	 public User findByUsername(String name) {
	        return userRepository.findByUsername(name).orElseThrow(
	                () -> new UsernameNotFoundException("User not found with username: " + name)
	        );
	    }
}
