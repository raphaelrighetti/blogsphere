package io.github.raphaelrighetti.blogsphere.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.raphaelrighetti.blogsphere.models.dto.UserReadDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserSignUpDTO;
import io.github.raphaelrighetti.blogsphere.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sign-up")
public class UserSignUpController {
	
	@Autowired
	private UserService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<UserReadDTO> signUp(@RequestBody @Valid UserSignUpDTO dto,
			UriComponentsBuilder uriBuilder) {
		UserReadDTO responseDTO = service.create(dto);
		URI uri = uriBuilder.path("/users/{id}").buildAndExpand(responseDTO.id()).toUri();
		
		return ResponseEntity.created(uri).body(responseDTO);
	}
	
}
