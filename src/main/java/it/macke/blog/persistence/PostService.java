package it.macke.blog.persistence;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.macke.blog.domain.Post;

@RequestScoped
public class PostService
{
	@PersistenceContext
	private EntityManager em;

	public Iterable<Post> findAll()
	{
		return em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
	}

	public Optional<Post> find(final Long id)
	{
		return Optional.ofNullable(em.find(Post.class, id));
	}
}
