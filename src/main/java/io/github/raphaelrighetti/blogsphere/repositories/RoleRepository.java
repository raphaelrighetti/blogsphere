package io.github.raphaelrighetti.blogsphere.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.raphaelrighetti.blogsphere.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
