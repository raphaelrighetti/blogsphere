package io.github.raphaelrighetti.blogsphere.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import io.github.raphaelrighetti.blogsphere.models.Blog;
import io.github.raphaelrighetti.blogsphere.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findByBlog(Blog blog, Pageable pageable);
	
}
