package io.github.raphaelrighetti.blogsphere.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.raphaelrighetti.blogsphere.models.dto.UserReadDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserUpdateDTO;
import io.github.raphaelrighetti.blogsphere.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<Page<UserReadDTO>> get(@PageableDefault Pageable pageable) {
		Page<UserReadDTO> page = service.get(pageable);
		
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserReadDTO> getById(@PathVariable Long id) {
		UserReadDTO responseDTO = service.getById(id);
		
		return ResponseEntity.ok(responseDTO);
	}
	
	@PutMapping("{id}")
	@Transactional
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO dto) {
		service.update(id, dto);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
