package io.github.raphaelrighetti.blogsphere.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.raphaelrighetti.blogsphere.exceptions.NotFoundException;
import io.github.raphaelrighetti.blogsphere.models.Role;
import io.github.raphaelrighetti.blogsphere.models.User;
import io.github.raphaelrighetti.blogsphere.models.dto.UserChangePasswordDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserListingDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserReadDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserSignUpDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserUpdateDTO;
import io.github.raphaelrighetti.blogsphere.repositories.RoleRepository;
import io.github.raphaelrighetti.blogsphere.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserReadDTO create(UserSignUpDTO dto) {
		Role userRole = roleRepository.getReferenceById(2L);
		
		User user = new User(dto);
		user.setPassword(passwordEncoder.encode(dto.password()));
		user.setRole(userRole);
		
		userRepository.save(user);
		
		return new UserReadDTO(user);
	}
	
	public Page<UserListingDTO> get(Pageable pageable) {
		return userRepository.findByActiveTrue(pageable).map(user -> new UserListingDTO(user));
	}
	
	public User getById(Long id) {
		if (!userRepository.existsById(id)) {
			throw new NotFoundException();
		}
		
		User user = userRepository.getReferenceById(id);
		
		return user;
	}
	
	public void update(Long id, UserUpdateDTO dto) {
		if (!userRepository.existsById(id)) {
			throw new NotFoundException();
		}
		
		User user = userRepository.getReferenceById(id);
		
		if (dto.userName() != null) {
			user.setPublicUserName(dto.userName());
		}
		
		if (dto.pictureUrl() != null) {
			user.setPictureUrl(dto.pictureUrl());
		}
		
		if (dto.description() != null) {
			user.setDescription(dto.description());
		}
	}
	
	public void changePassword(Long id, UserChangePasswordDTO dto) {
		if (!userRepository.existsById(id)) {
			throw new NotFoundException();
		}
		
		User user = userRepository.getReferenceById(id);
		user.setPassword(passwordEncoder.encode(dto.password()));
	}
	
	public void delete(Long id) {
		if (!userRepository.existsById(id)) {
			throw new NotFoundException();
		}
		
		User user = userRepository.getReferenceById(id);
		user.deactivate();
	}
	
	public void activate(Long id) {
		if (!userRepository.existsById(id)) {
			throw new NotFoundException();
		}
		
		User user = userRepository.getReferenceById(id);
		user.activate();
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		return userRepository.findByEmail(login);
	}

}
