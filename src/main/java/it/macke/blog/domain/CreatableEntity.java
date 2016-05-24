package it.macke.blog.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class CreatableEntity
{
	@Column
	private LocalDateTime createdAt;

	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(final LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
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
}
