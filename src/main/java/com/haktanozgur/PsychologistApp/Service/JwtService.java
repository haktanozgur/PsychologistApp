package com.haktanozgur.PsychologistApp.Service;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.haktanozgur.PsychologistApp.Entity.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	private static final String SECRET_KEY = "3778214125442A472D4B6150645267556B58703273357638792F423F4528482B";
	
	public String extractUsername(String token) {
		
		return extractClaim(token, Claims :: getSubject);
	}
	
	public <T> T extractClaim(String token , Function<Claims , T> claimsResolver ) {
		
		final Claims claims = extractAllClaims(token);
		
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(User userDetails) {
		
		return generateToken(new HashMap<>(), userDetails);
	}
	
	
	public String generateToken(
			Map<String, Object> extraClaims,
			User userDetails) {
		
		return Jwts.builder().setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.claim("user_id", userDetails.getId())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+  1000 * 60 * 60))
				.signWith(getSignInKey() , SignatureAlgorithm.HS256)
				.compact();
				
	}
	public boolean isTokenValid(String token , UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	

	private boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new java.util.Date());
	}

	private java.util.Date extractExpiration(String token) {
		
		return extractClaim(token, Claims ::getExpiration);
	}

	private Claims extractAllClaims(String token ) {
		
		return Jwts.parserBuilder().setSigningKey(getSignInKey())
				.build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}

