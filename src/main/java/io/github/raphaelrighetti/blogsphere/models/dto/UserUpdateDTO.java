package io.github.raphaelrighetti.blogsphere.models.dto;

import io.github.raphaelrighetti.blogsphere.validations.constraints.UrlConstraint;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(@Size(min = 3) String userName, @UrlConstraint String pictureUrl, String description) {

}
