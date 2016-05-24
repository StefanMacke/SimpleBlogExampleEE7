package it.macke.blog.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Post extends CreatableEntity
{
	@Id
	@GeneratedValue
	private long id;

	private String title;
	private String content;

	@OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private final List<Comment> comments = new ArrayList<>();

	protected Post()
	{}

	public Post(final String title, final String content)
	{
		this.title = title;
		this.content = content;
	}

	public long getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getContent()
	{
		return content;
	}

	public void addComment(final Comment comment)
	{
		comments.add(comment);
		comment.setPost(this);
	}

	public Iterable<Comment> getComments()
	{
		return comments;
	}

	public boolean hasComments()
	{
		return !comments.isEmpty();
	}

	public Optional<Comment> getLatestComment()
	{
		if (comments.isEmpty())
		{
			return Optional.empty();
		}
		return comments
				.stream()
				.max((c1, c2) -> c1.getCreatedAt().compareTo(c2.getCreatedAt()));
	}

	@Override
	public String toString()
	{
		return "Post " + getId() + ", title " + getTitle();
	}
}
