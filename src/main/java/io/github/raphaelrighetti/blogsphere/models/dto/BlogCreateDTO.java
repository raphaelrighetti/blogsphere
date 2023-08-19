package io.github.raphaelrighetti.blogsphere.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BlogCreateDTO(@NotEmpty String title, @NotEmpty String description, @NotNull @Min(1) Long userId) {
	
}
