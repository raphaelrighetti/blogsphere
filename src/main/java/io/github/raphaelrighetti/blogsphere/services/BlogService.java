package io.github.raphaelrighetti.blogsphere.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.raphaelrighetti.blogsphere.exceptions.NotFoundException;
import io.github.raphaelrighetti.blogsphere.models.Blog;
import io.github.raphaelrighetti.blogsphere.models.User;
import io.github.raphaelrighetti.blogsphere.models.dto.BlogCreateDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.BlogListingDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.BlogReadDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.BlogUpdateDTO;
import io.github.raphaelrighetti.blogsphere.repositories.BlogRepository;
import io.github.raphaelrighetti.blogsphere.services.util.CheckOwnerService;

@Service
public class BlogService {

	@Autowired
	private BlogRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CheckOwnerService checkOwnerService;
	
	public BlogReadDTO create(BlogCreateDTO dto) {
		User owner = userService.getById(dto.userId());
		Blog blog = new Blog(dto);
		blog.setUser(owner);
		
		repository.save(blog);
		
		return new BlogReadDTO(blog);
	}
	
	public Page<BlogListingDTO> get(Pageable pageable) {
		return repository.findAll(pageable).map(blog -> new BlogListingDTO(blog));
	}
	
	public Blog getById(Long id) {
		if (!repository.existsById(id)) {
			throw new NotFoundException();
		}
		
		Blog blog = repository.getReferenceById(id);
		
		return blog;
	}
	
	public void update(Long id, BlogUpdateDTO dto, String header) {
		if (!repository.existsById(id)) {
			throw new NotFoundException();
		}
		
		Blog blog = repository.getReferenceById(id);
		checkOwnerService.checkOwnerUser(blog.getUser().getId(), header);
		blog.update(dto);
	}
	
	public void delete(Long id, String header) {
		if (!repository.existsById(id)) {
			throw new NotFoundException();
		}
	
		Blog blog = repository.getReferenceById(id);
		checkOwnerService.checkOwnerUser(blog.getUser().getId(), header);
		repository.delete(blog);
	}
	
}
