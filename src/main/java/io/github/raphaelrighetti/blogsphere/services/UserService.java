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
import io.github.raphaelrighetti.blogsphere.models.User;
import io.github.raphaelrighetti.blogsphere.models.dto.UserChangePasswordDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserListingDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserReadDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserSignUpDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.UserUpdateDTO;
import io.github.raphaelrighetti.blogsphere.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserReadDTO create(UserSignUpDTO dto) {
		User user = new User(dto);
		user.setPassword(passwordEncoder.encode(dto.password()));
		
		repository.save(user);
		
		return new UserReadDTO(user);
	}
	
	public Page<UserListingDTO> get(Pageable pageable) {
		return repository.findByActiveTrue(pageable).map(user -> new UserListingDTO(user));
	}
	
	public UserReadDTO getById(Long id) {
		if (!repository.existsById(id)) {
			throw new NotFoundException();
		}
		
		User user = repository.getReferenceById(id);
		
		return new UserReadDTO(user);
	}
	
	public void update(Long id, UserUpdateDTO dto) {
		if (!repository.existsById(id)) {
			throw new NotFoundException();
		}
		
		User user = repository.getReferenceById(id);
		
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
		if (!repository.existsById(id)) {
			throw new NotFoundException();
		}
		
		User user = repository.getReferenceById(id);
		user.setPassword(passwordEncoder.encode(dto.password()));
	}
	
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new NotFoundException();
		}
		
		User user = repository.getReferenceById(id);
		user.deactivate();
	}
	
	public void activate(Long id) {
		if (!repository.existsById(id)) {
			throw new NotFoundException();
		}
		
		User user = repository.getReferenceById(id);
		user.activate();
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		return repository.findByEmail(login);
	}

}
