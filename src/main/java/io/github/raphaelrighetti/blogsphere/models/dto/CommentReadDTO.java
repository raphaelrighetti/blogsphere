package io.github.raphaelrighetti.blogsphere.models.dto;

import java.util.List;

import io.github.raphaelrighetti.blogsphere.models.Comment;

public record CommentReadDTO(Long id, String content, Long postId, Long parentCommentId, Long userId, List<CommentReadDTO> answers) {

	public CommentReadDTO(Comment comment) {
		this(comment.getId(), comment.getContent(), comment.getPost().getId(), comment.getParentComment().getId(),
				comment.getUser().getId(), comment.getAnswersDTO());
	}
	
}
