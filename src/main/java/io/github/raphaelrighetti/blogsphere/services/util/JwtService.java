package io.github.raphaelrighetti.blogsphere.services.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.github.raphaelrighetti.blogsphere.models.User;
import io.github.raphaelrighetti.blogsphere.models.dto.UserTokenClaimsDTO;

@Service
public class JwtService {

	@Value("${security.jwt-secret}")
	String secret;
	
	public String createToken(User user) {
		Algorithm algorithm = Algorithm.HMAC512(secret);
		
		return JWT.create()
				.withIssuer("Raphael Righetti")
				.withSubject(user.getEmail())
				.withClaim("id", user.getId())
				.withClaim("role", user.getRole().getName())
				.withExpiresAt(getExpiresAt())
				.sign(algorithm);
	}
	
	public UserTokenClaimsDTO getClaims(String header) {
		String token = getTokenFromHeader(header);
		Algorithm algorithm = Algorithm.HMAC512(secret);
		
		DecodedJWT verifiedToken = JWT.require(algorithm)
			.withIssuer("Raphael Righetti")
			.build()
			.verify(token);
		
		return new UserTokenClaimsDTO(verifiedToken.getSubject(), verifiedToken.getClaim("id").asLong(),
				verifiedToken.getClaim("role").asString());
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
