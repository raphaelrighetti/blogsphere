package io.github.raphaelrighetti.blogsphere.controllers.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.raphaelrighetti.blogsphere.exceptions.NotFoundException;
import io.github.raphaelrighetti.blogsphere.exceptions.UnauthorizedException;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Void> notFoundException() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<CustomFieldErrorDTO>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<CustomFieldErrorDTO> errorsDTO = ex.getFieldErrors().stream().map(error -> new CustomFieldErrorDTO(error)).toList();
		
		return ResponseEntity.badRequest().body(errorsDTO);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Void> unauthorizedException() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	private record CustomFieldErrorDTO(String field, String message) {
		
		public CustomFieldErrorDTO(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
		
	}
	
}
