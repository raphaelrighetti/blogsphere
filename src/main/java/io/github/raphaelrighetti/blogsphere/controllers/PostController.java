package io.github.raphaelrighetti.blogsphere.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.raphaelrighetti.blogsphere.models.dto.PostCreateDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.PostListingDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.PostReadDTO;
import io.github.raphaelrighetti.blogsphere.services.PostService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class PostController {

	@Autowired
	private PostService service;
	
	@PostMapping("/blogs/{blogId}/posts")
	@Transactional
	public ResponseEntity<PostReadDTO> create(@PathVariable Long blogId, @RequestHeader("Authorization") String header,
			@RequestBody @Valid PostCreateDTO dto, UriComponentsBuilder uriBuilder) {
		PostReadDTO responseDTO = service.create(blogId, header, dto);
		URI uri = uriBuilder.path("posts/{id}").buildAndExpand(responseDTO.id()).toUri();
		
		return ResponseEntity.created(uri).body(responseDTO);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<Page<PostListingDTO>> get(Pageable pageable) {
		Page<PostListingDTO> page = service.get(pageable);
		
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/posts/{id}")
	public ResponseEntity<PostReadDTO> getById(@PathVariable Long id) {
		PostReadDTO responseDTO = new PostReadDTO(service.getById(id));
		
		return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/blogs/{blogId}/posts")
	public ResponseEntity<Page<PostListingDTO>> getBlogPosts(@PathVariable Long blogId, Pageable pageable) {
		Page<PostListingDTO> page = service.getBlogPosts(blogId, pageable);
		
		return ResponseEntity.ok(page);
	}
	
}
