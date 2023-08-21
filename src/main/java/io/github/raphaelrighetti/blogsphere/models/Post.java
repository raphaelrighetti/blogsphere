package io.github.raphaelrighetti.blogsphere.models;

import java.util.ArrayList;
import java.util.List;

import io.github.raphaelrighetti.blogsphere.models.dto.CommentReadDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.PostCreateDTO;
import io.github.raphaelrighetti.blogsphere.models.dto.ReactionReadDTO;
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

@Table(name = "posts")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String title;
	@NotNull
	private String description;
	@NotNull
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blog_id")
	private Blog blog;
	@OneToMany(mappedBy = "post")
	private List<Reaction> reactions;
	@OneToMany(mappedBy = "post")
	private List<Comment> comments;
	
	public Post(PostCreateDTO dto) {
		title = dto.title();
		description = dto.description();
		content = dto.content();
	}
	
	public List<ReactionReadDTO> getReactionsDTO() {
		if (reactions == null) {
			return new ArrayList<>();
		}
		
		return reactions.stream().map(reaction -> new ReactionReadDTO(reaction)).toList();
	}
	
	public List<CommentReadDTO> getCommentsDTO() {
		if (comments == null) {
			return new ArrayList<>();
		}
		
		return comments.stream().map(comment -> new CommentReadDTO(comment)).toList();
	}

}
