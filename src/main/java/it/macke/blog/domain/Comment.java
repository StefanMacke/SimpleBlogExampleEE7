package it.macke.blog.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment
{
	@Id
	@GeneratedValue
	private long id;

	private String content;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id")
	private Post post;

	protected Comment()
	{}

	public Comment(final String content)
	{
		this.content = content;
	}

	public long getId()
	{
		return id;
	}

	public String getContent()
	{
		return content;
	}

	public Post getPost()
	{
		return post;
	}

	public void setPost(final Post post)
	{
		this.post = post;
	}
}
