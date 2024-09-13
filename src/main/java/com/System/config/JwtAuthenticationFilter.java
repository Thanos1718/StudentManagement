package com.System.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.System.Service.JwtService;
import com.System.Service.UserDetailsServiceImpl;
import com.System.Service.UserImpl;

import ch.qos.logback.core.net.SyslogOutputStream;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtService jwtservice;
	private final UserDetailsServiceImpl userDetailsService;







	public JwtAuthenticationFilter(JwtService jwtservice, UserDetailsServiceImpl userDetailsService) {
		this.jwtservice = jwtservice;
		this.userDetailsService = userDetailsService;
	}







	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader==null||!authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authHeader.substring(7);
		System.out.println("Token is"+token);
		String gmail=jwtservice.extractGmail(token);
		System.out.println("UserName Obtained is"+gmail);
		
		if(gmail!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			System.out.println("Enter if");

			UserDetails userDetails=userDetailsService.loadUserByUsername(gmail);
			System.out.println("check userdetails"+userDetails.getUsername());
			if(jwtservice.isValid(token, userDetails)) {
				System.out.println("ValidationSuccess");

				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);

			}
			System.out.println("Exit");
		} 
		filterChain.doFilter(request, response);
		
	}

}
