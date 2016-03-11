package it.macke.blog.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Post
{
	@Id
	@GeneratedValue
	private long id;

	private String title;
	private String content;

	@OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
	private final Set<Comment> comments = new HashSet<>();

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
}
