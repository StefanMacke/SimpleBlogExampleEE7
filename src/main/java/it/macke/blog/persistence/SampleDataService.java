package it.macke.blog.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import it.macke.blog.domain.Post;

@ApplicationScoped
public class SampleDataService
{
	@PersistenceContext
	private EntityManager em;

	@Transactional
	void addPosts(@Observes @Initialized(ApplicationScoped.class) final Object event)
	{
		try
		{
			if (em.createQuery("SELECT COUNT(p) FROM Post p", Long.class).getSingleResult() == 0)
			{
				em.persist(new Post("My first post", "This is my very first post"));
				em.persist(new Post("My second post", "Let's try again! This is my second post"));
				em.persist(new Post("My third post", "Finally, this is my third post"));
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}
}
