package io.github.raphaelrighetti.blogsphere.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.github.raphaelrighetti.blogsphere.exceptions.UnauthorizedException;
import io.github.raphaelrighetti.blogsphere.models.dto.UserReadDTO;

@Service
public class CheckOwnerService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	public void isOwner(Long id, String token) {
		String subject = jwtService.getSubject(token);
		UserDetails authenticatedUser = userService.loadUserByUsername(subject);
		
		UserReadDTO user = userService.getById(id);
		
		if (!user.login().equals(authenticatedUser.getUsername())) {
			throw new UnauthorizedException();
		}
	}
	
}
