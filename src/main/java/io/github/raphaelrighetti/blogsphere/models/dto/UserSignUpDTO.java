package io.github.raphaelrighetti.blogsphere.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserSignUpDTO(@Email @NotEmpty String login, @NotEmpty @Size(min = 8) String password) {

}
