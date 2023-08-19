package io.github.raphaelrighetti.blogsphere.models.dto;

import io.github.raphaelrighetti.blogsphere.models.Blog;

public record BlogListingDTO(Long id, String title, String description, Long userId) {
	
	public BlogListingDTO(Blog blog) {
		this(blog.getId(), blog.getTitle(), blog.getDescription(), blog.getUser().getId());
	}
	
}
