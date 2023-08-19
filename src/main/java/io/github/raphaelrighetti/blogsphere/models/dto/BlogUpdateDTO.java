package io.github.raphaelrighetti.blogsphere.models.dto;

import jakarta.validation.constraints.Size;

public record BlogUpdateDTO(@Size(min = 1) String title, @Size(min = 1) String description) {
	
}
