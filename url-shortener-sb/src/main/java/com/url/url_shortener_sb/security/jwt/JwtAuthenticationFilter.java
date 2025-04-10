package com.url.url_shortener_sb.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtils jwtTokenProvider;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			//get jwt from token
			//validate token
			//if valid get user details
			//get user name -> load user -> set auth context
			String jwt = jwtTokenProvider.getJwtFromHeader(request);
			
			if(jwt != null && jwtTokenProvider.validateToken(jwt)) {
				String username = jwtTokenProvider.getUserNameFromJwtToken(jwt);
				UserDetails userDetails = userDetailService.loadUserByUsername(username);
				if(userDetails != null) {
					UsernamePasswordAuthenticationToken authenticaion = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authenticaion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticaion);
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		filterChain.doFilter(request, response);
	}

}
