package io.github.raphaelrighetti.blogsphere.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.raphaelrighetti.blogsphere.models.dto.BlogCreateDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.BlogListingDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.BlogReadDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.BlogUpdateDTO;
import io.github.raphaelrighetti.blogsphere.services.BlogService;
import io.github.raphaelrighetti.blogsphere.services.util.CheckOwnerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blogs")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CheckOwnerService checkOwnerService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<BlogReadDTO> create(@RequestBody @Valid BlogCreateDTO dto, UriComponentsBuilder uriBuilder,
			@RequestHeader("Authorization") String header) {
		checkOwnerService.checkOwnerUser(dto.userId(), header);
		BlogReadDTO responseDTO = blogService.create(dto);
		URI uri = uriBuilder.path("/blogs/{id}").buildAndExpand(responseDTO.id()).toUri();
		
		return ResponseEntity.created(uri).body(responseDTO);
	}
	
	@GetMapping
	public ResponseEntity<Page<BlogListingDTO>> get(Pageable pageable) {
		Page<BlogListingDTO> page = blogService.get(pageable);
		
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BlogReadDTO> getById(@PathVariable Long id) {
		BlogReadDTO dto = new BlogReadDTO(blogService.getById(id));
		
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid BlogUpdateDTO dto,
			@RequestHeader("Authorization") String header) {
		blogService.update(id, dto, header);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String header) {
		blogService.delete(id, header);
		
		return ResponseEntity.noContent().build();
	}
	
}
