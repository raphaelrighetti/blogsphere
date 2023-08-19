package io.github.raphaelrighetti.blogsphere.models.dto;

import io.github.raphaelrighetti.blogsphere.models.ReactionType;

public record ReactionReadDTO(Long id, ReactionType type, Long postId, Long commentId, Long userId) {

}
