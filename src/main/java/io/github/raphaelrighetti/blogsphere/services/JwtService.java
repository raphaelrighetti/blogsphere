package io.github.raphaelrighetti.blogsphere.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {

	@Value("${security.jwt-secret}")
	String secret;
	
	public String createToken(UserDetails userDetails) {
		Algorithm algorithm = Algorithm.HMAC512(secret);
		
		return JWT.create()
				.withIssuer("Raphael Righetti")
				.withSubject(userDetails.getUsername())
				.withExpiresAt(getExpiresAt())
				.sign(algorithm);
	}
	
	public String getSubject(String header) {
		String token = getTokenFromHeader(header);
		Algorithm algorithm = Algorithm.HMAC512(secret);
		
		return JWT.require(algorithm)
			.withIssuer("Raphael Righetti")
			.build()
			.verify(token)
			.getSubject();
	}
	
	private Instant getExpiresAt() {
		return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
	}
	
	private String getTokenFromHeader(String header) {
		if (header == null) {
			return null;
		}
		
		return header.replace("Bearer ", "");
	}
	
}
