package it.macke.blog.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Comment
{
	@Id
	@GeneratedValue
	private long id;

	private String content;

	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id")
	private Post post;

	protected Comment()
	{}

	public Comment(final String content)
	{
		this.content = content;
	}

	@PreUpdate
	@PrePersist
	public void updateCreatedAt()
	{
		final LocalDateTime now = LocalDateTime.now();
		if (getCreatedAt() == null)
		{
			setCreatedAt(now);
		}
	}

	public long getId()
	{
		return id;
	}

	public String getContent()
	{
		return content;
	}

	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(final LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
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
