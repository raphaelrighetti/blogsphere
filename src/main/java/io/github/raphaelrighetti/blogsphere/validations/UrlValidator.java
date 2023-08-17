package io.github.raphaelrighetti.blogsphere.validations;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import io.github.raphaelrighetti.blogsphere.validations.constraints.UrlConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<UrlConstraint, String> {

	@Override
	public void initialize(UrlConstraint url) {
	}
	
	@Override
	public boolean isValid(String url, ConstraintValidatorContext context) {
		if (url == null || url.isEmpty()) {
			return true;
		}
		
		try {
			new URL(url).toURI();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (URISyntaxException e) {
			return false;
		}
	}

}
