package io.github.raphaelrighetti.blogsphere.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(@Email String login, @Size(min = 8) String password) {

}
