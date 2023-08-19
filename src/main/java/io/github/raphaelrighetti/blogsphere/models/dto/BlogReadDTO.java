package io.github.raphaelrighetti.blogsphere.models.dto;

import java.util.List;

import io.github.raphaelrighetti.blogsphere.models.Blog;

public record BlogReadDTO(Long id, String title, String description, Long userId, List<PostListingDTO> posts) {
	
	public BlogReadDTO(Blog blog) {
		this(blog.getId(), blog.getTitle(), blog.getDescription(), blog.getUser().getId(),
				blog.getPosts().stream().map(post -> new PostListingDTO(post)).toList());
	}
	
}
