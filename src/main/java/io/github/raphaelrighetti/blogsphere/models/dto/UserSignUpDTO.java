package io.github.raphaelrighetti.blogsphere.models.dto;

import io.github.raphaelrighetti.blogsphere.validations.constraints.UrlConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserSignUpDTO(@Email @NotEmpty String email, @NotEmpty @Size(min = 3) String userName,
		@NotEmpty @Size(min = 8) String password, @UrlConstraint String pictureUrl, String description) {

}
