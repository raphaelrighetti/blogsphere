package io.github.raphaelrighetti.blogsphere.models.dto;

import java.util.List;

public record PostReadDTO(Long id, String title, String description, String content,
		Long blogId, List<ReactionReadDTO> reactions, List<CommentReadDTO> comments) {

}
