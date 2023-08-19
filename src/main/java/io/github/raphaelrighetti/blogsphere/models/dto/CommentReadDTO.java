package io.github.raphaelrighetti.blogsphere.models.dto;

import java.util.List;

public record CommentReadDTO(Long id, String content, Long postId, Long parentCommentId, Long userId, List<CommentReadDTO> answers) {

}
