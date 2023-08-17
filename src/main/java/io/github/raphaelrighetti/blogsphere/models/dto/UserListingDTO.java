package io.github.raphaelrighetti.blogsphere.models.dto;

import io.github.raphaelrighetti.blogsphere.models.User;

public record UserListingDTO(Long id, String userName, String pictureUrl, String description, Boolean active) {
	
	public UserListingDTO(User user) {
		this(user.getId(), user.getPublicUserName(), user.getPictureUrl(), user.getDescription(), user.getActive());
	}
	
}
