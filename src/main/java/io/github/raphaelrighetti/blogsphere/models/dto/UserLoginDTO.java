package io.github.raphaelrighetti.blogsphere.models.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginDTO(@NotEmpty String email, @NotEmpty String password) {

}
