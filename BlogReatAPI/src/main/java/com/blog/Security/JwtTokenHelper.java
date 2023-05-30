package com.blog.Security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenHelper {

	
	//private  static final long serialVersionUID = -2550185165626007488L;
	
	public static final long JWT_TOKEN_VALIDITY = 10*60*60;
	
	//private String secret = "java";
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	
	// retrive username from jwt token
	
	public String getUsernameFromToken(String token) {
		
		return getClaimsFromToken(token, Claims::getSubject);
		
	}
	
	
	public Date getExpirationDateFromToken(String token) {
		
		
		return getClaimsFromToken(token, Claims::getExpiration);
		
	}

	
	
	public <T> T getClaimsFromToken(String token, Function<Claims,T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
		
		
	}
	
	private Claims getAllClaimsFromToken(String token) {
		
		return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
		
		
	}
	
	 private Boolean isTokenExpired(String token) {
		 
		// final Date expirationDate = getExpirationDateFromToken(token);
		 //return expirationDate.before(new Date());
		 
		 return getExpirationDateFromToken(token).before(new Date());
	 }
	
	
	 public String generateToken(String username) {
		 
		 Map<String,Object> claims =new HashMap<>();
		 return doGenerateToken(claims, username);
	 }
	
	 
	
	private String doGenerateToken(Map<String,Object> claims, String username) {
		 
		 
		return Jwts.builder().setClaims(claims).setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY*1000)).signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
		 
		 
		 
	 }
	
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));		
	}
	
	 private Key getSignKey() {
	        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }
	
	
	
	
}
