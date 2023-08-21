package io.github.raphaelrighetti.blogsphere.models.dto;

import io.github.raphaelrighetti.blogsphere.models.Reaction;
import io.github.raphaelrighetti.blogsphere.models.ReactionType;

public record ReactionReadDTO(Long id, ReactionType type, Long postId, Long commentId, Long userId) {

	public ReactionReadDTO(Reaction reaction) {
		this(reaction.getId(), reaction.getType(), reaction.getPost().getId(),
				reaction.getComment().getId(), reaction.getUser().getId());
	}
	
}
