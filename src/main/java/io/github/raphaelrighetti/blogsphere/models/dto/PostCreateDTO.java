package io.github.raphaelrighetti.blogsphere.models.dto;

import jakarta.validation.constraints.NotEmpty;

public record PostCreateDTO(@NotEmpty String title, @NotEmpty String description, @NotEmpty String content) {

}
