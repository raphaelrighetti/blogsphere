package io.github.raphaelrighetti.blogsphere.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.github.raphaelrighetti.blogsphere.exceptions.UnauthorizedException;
import io.github.raphaelrighetti.blogsphere.models.dto.UserReadDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserTokenClaimsDTO;

@Service
public class CheckOwnerService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	public void checkOwner(Long id, String token) {
		String subject = jwtService.getClaims(token).email();
		UserDetails authenticatedUser = userService.loadUserByUsername(subject);
		
		if (authenticatedUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			return;
		}
		
		UserReadDTO user = userService.getById(id);
		
		if (!user.email().equals(authenticatedUser.getUsername())) {
			throw new UnauthorizedException();
		}
	}
	
	public void checkOwnerUser(Long id, String token) {
		UserTokenClaimsDTO claims = jwtService.getClaims(token);
		
		if (claims.role().equals("ROLE_ADMIN")) {
			return;
		}
		
		if (claims.id() != id) {
			throw new UnauthorizedException();
		}
	}
	
}
