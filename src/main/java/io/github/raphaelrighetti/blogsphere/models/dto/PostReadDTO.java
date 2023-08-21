package io.github.raphaelrighetti.blogsphere.models.dto;

import java.util.List;

import io.github.raphaelrighetti.blogsphere.models.Post;

public record PostReadDTO(Long id, String title, String description, String content,
		Long blogId, List<ReactionReadDTO> reactions, List<CommentReadDTO> comments) {
	
	public PostReadDTO(Post post) {
		this(post.getId(), post.getTitle(), post.getDescription(), post.getContent(),
				post.getBlog().getId(), post.getReactionsDTO(), post.getCommentsDTO());
	}

}
