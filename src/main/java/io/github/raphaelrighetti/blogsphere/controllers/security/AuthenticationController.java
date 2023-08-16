package io.github.raphaelrighetti.blogsphere.controllers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.raphaelrighetti.blogsphere.models.dto.UserLoginDTO;
import io.github.raphaelrighetti.blogsphere.services.JwtService;
import io.github.raphaelrighetti.blogsphere.services.UserService;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping
	public ResponseEntity<AuthenticationDTO> authenticate(@RequestBody UserLoginDTO dto) {
		UserDetails userDetails = userService.loadUserByUsername(dto.login());
		
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.login(), dto.password()));
		
		if (auth.isAuthenticated()) {
			String token = jwtService.createToken(userDetails);
			
			return ResponseEntity.ok(new AuthenticationDTO(token));
		}
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	private record AuthenticationDTO(String token) {
		
	}
	
}
