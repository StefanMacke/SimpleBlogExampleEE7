package it.macke.blog.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import it.macke.blog.domain.Comment;
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
				final Post hello = new Post("Hello Brazil!", "Yes, yes, hello indeed!");
				hello.addComment(new Comment("Yes, I agree with the hello!"));
				hello.addComment(new Comment("Me too, me too!"));
				em.persist(hello);
				em.persist(new Post("Better fill it in, then", "Aye, aye, sir!"));
				em.persist(new Post("Let's just add a third, for good measure", "Oh yeah!"));
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}
}
