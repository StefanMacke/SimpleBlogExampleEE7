package it.macke.blog.persistence;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import it.macke.blog.domain.Comment;
import it.macke.blog.domain.Post;

@RequestScoped
public class CommentRepository
{
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void persist(final Long postId, final Comment c)
	{
		final Post post = em.find(Post.class, postId);
		final Comment comment = em.merge(c);
		post.addComment(comment);
		em.persist(post);
	}
}
