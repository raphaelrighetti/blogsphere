package io.github.raphaelrighetti.blogsphere.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import io.github.raphaelrighetti.blogsphere.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Page<User> findByActiveTrue(Pageable pageable);
	
	UserDetails findByLogin(String login);
	
}
