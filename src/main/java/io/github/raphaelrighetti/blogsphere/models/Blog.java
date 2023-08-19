package io.github.raphaelrighetti.blogsphere.models;

import java.util.ArrayList;
import java.util.List;

import io.github.raphaelrighetti.blogsphere.models.dto.BlogCreateDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.BlogUpdateDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "blogs")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String title;
	@NotNull
	private String description;
	
	@OneToMany(mappedBy = "blog")
	private List<Post> posts;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	public Blog(BlogCreateDTO dto) {
		title = dto.title();
		description = dto.description();
	}
	
	public List<Post> getPosts() {
		if (posts == null) {
			return new ArrayList<>();
		}
		
		return posts;
	}
	
	public void update(BlogUpdateDTO dto) {
		if (dto.title() != null) {
			title = dto.title();
		}
		
		if (dto.description() != null) {
			description = dto.description();
		}
	}

}
