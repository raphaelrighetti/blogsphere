package io.github.raphaelrighetti.blogsphere.models.dto;

import io.github.raphaelrighetti.blogsphere.models.User;

public record UserReadDTO(Long id, String email, String userName, String pictureUrl, String description, Boolean active) {
	
	public UserReadDTO(User user) {
		this(user.getId(), user.getEmail(), user.getPublicUserName(), user.getPictureUrl(), user.getDescription(), user.getActive());
	}
	
}
