package io.github.raphaelrighetti.blogsphere.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserChangePasswordDTO(@NotEmpty @Size(min = 8) String password) {

}
