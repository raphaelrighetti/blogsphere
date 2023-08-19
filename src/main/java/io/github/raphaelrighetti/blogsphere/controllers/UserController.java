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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.raphaelrighetti.blogsphere.models.dto.UserChangePasswordDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserListingDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserReadDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserUpdateDTO;
import io.github.raphaelrighetti.blogsphere.services.UserService;
import io.github.raphaelrighetti.blogsphere.services.util.CheckOwnerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CheckOwnerService checkOwnerService;
	
	@GetMapping
	public ResponseEntity<Page<UserListingDTO>> get(@PageableDefault Pageable pageable) {
		Page<UserListingDTO> page = userService.get(pageable);
		
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserReadDTO> getById(@PathVariable Long id, @RequestHeader("Authorization") String header) {
		UserReadDTO responseDTO = new UserReadDTO(userService.getById(id));
		
		return ResponseEntity.ok(responseDTO);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO dto,
			@RequestHeader("Authorization") String header) {
		checkOwnerService.checkOwner(id, header);
		userService.update(id, dto);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}/change-password")
	@Transactional
	public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody @Valid UserChangePasswordDTO dto, 
			@RequestHeader("Authorization") String header) {
		checkOwnerService.checkOwner(id, header);
		userService.changePassword(id, dto);
		
		// TODO: Implement verification by email logic
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String header) {
		checkOwnerService.checkOwner(id, header);
		userService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
