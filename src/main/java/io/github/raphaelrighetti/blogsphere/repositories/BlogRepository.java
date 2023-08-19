package io.github.raphaelrighetti.blogsphere.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.raphaelrighetti.blogsphere.models.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
