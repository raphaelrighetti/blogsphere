package io.github.raphaelrighetti.blogsphere.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.raphaelrighetti.blogsphere.exceptions.NotFoundException;
import io.github.raphaelrighetti.blogsphere.models.Blog;
import io.github.raphaelrighetti.blogsphere.models.Post;
import io.github.raphaelrighetti.blogsphere.models.dto.PostCreateDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.PostListingDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.PostReadDTO;
import io.github.raphaelrighetti.blogsphere.repositories.PostRepository;
import io.github.raphaelrighetti.blogsphere.services.util.CheckOwnerService;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CheckOwnerService checkOwnerService;
	
	public PostReadDTO create(Long blogId, String header, PostCreateDTO dto) {
		Blog blog = blogService.getById(blogId);
		checkOwnerService.checkOwnerUser(blog.getUser().getId(), header);
		
		Post post = new Post(dto);
		post.setBlog(blog);
		
		repository.save(post);
		
		return new PostReadDTO(post);
	}
	
	public Page<PostListingDTO> get(Pageable pageable) {
		return repository.findAll(pageable).map(post -> new PostListingDTO(post));
	}
	
	public Post getById(Long id) {
		if (!repository.existsById(id)) {
			throw new NotFoundException();
		}
		
		return repository.getReferenceById(id);
	}
	
	public Page<PostListingDTO> getBlogPosts(Long blogId, Pageable pageable) {
		Blog blog = blogService.getById(blogId);
		Page<PostListingDTO> page = 
				repository.findByBlog(blog, pageable).map(post -> new PostListingDTO(post));
		
		return page;
	}
	
}
