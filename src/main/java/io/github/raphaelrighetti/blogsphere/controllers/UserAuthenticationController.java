package io.github.raphaelrighetti.blogsphere.controllers;

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

import io.github.raphaelrighetti.blogsphere.models.User;
import io.github.raphaelrighetti.blogsphere.models.dto.UserLoginDTO;
import io.github.raphaelrighetti.blogsphere.services.UserService;
import io.github.raphaelrighetti.blogsphere.services.util.JwtService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class UserAuthenticationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping
	public ResponseEntity<AuthenticationDTO> authenticate(@RequestBody @Valid UserLoginDTO dto) {
		UserDetails userDetails = userService.loadUserByUsername(dto.email());
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
		
		if (auth.isAuthenticated()) {
			String token = jwtService.createToken((User) userDetails);
			
			return ResponseEntity.ok(new AuthenticationDTO(token));
		}
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	private record AuthenticationDTO(String token) {
		
	}
	
}
