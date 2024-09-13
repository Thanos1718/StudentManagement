package com.System.Service;


import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.System.Entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private String Secret_Key="8ac792bf04fa1b77651cb012bdcdca2b4192b5e70d56f6f82a76405cd6a598a5";

	
	public String extractGmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public boolean isValid(String token,UserDetails user) {
		String gmail=extractGmail(token);
		System.out.println("username extracted"+gmail);
		System.out.println("username in userdetails"+user.getUsername());
		System.out.println("Token Expired"+isTokenExpired(token));		
		return (gmail.equals(user.getUsername()))&& !isTokenExpired(token);
	}
	private boolean isTokenExpired(String token) {
		System.out.println("expiration date"+extractExpiration(token));
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	private <T> T extractClaim(String token,Function<Claims, T> resolver) {
		Claims claims=extractAllClaims(token);
		return resolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token)
	{
		return Jwts
				.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String genrateToken(User login)
	{
		String token = Jwts.builder()
			    .setSubject(login.getUseremail())  
			    .setIssuedAt(new Date(System.currentTimeMillis()))  
			    .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))  
			    .signWith(getSignKey())  
			    .compact(); 
		System.out.println("dat"+new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        return token;
	}
	
	private SecretKey getSignKey() {
		byte[] keyBytes=Decoders.BASE64URL.decode(Secret_Key);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}

