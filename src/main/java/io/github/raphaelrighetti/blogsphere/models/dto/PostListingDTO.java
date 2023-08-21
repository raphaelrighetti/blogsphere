package io.github.raphaelrighetti.blogsphere.models.dto;

import io.github.raphaelrighetti.blogsphere.models.Post;

public record PostListingDTO(Long id, String title, String description, Long blogId,
		Integer reactionCount, Integer commentCount) {
	
	public PostListingDTO(Post post) {
		this(post.getId(), post.getTitle(), post.getDescription(), post.getBlog().getId(),
				post.getReactionsDTO().size(), post.getCommentsDTO().size());
	}

}
