package io.github.raphaelrighetti.blogsphere.models.dto;

import io.github.raphaelrighetti.blogsphere.models.User;

public record UserReadDTO(Long id, String login, Boolean active) {
	
	public UserReadDTO(User user) {
		this(user.getId(), user.getLogin(), user.getActive());
	}
	
}
