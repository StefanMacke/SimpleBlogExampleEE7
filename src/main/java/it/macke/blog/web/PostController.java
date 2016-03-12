package it.macke.blog.web;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

import it.macke.blog.domain.Post;
import it.macke.blog.persistence.PostService;

@Named
@RequestScoped
public class PostController extends Controller
{
	@Inject
	private PostService service;

	public Iterable<Post> getPosts()
	{
		return service.findAll();
	}

	public Post getCurrentPost()
	{
		try
		{
			final Optional<String> idParam = getParameter("id");
			if (!idParam.isPresent())
			{
				throw new IllegalArgumentException();
			}
			final Long id = Long.parseLong(idParam.get());
			final Post p = service.find(id);
			if (p == null)
			{
				throw new EntityNotFoundException();
			}
			return p;
		}
		catch (final Exception e)
		{
			addMessage("Post not found");
			return new Post("Not found", "No content");
		}
	}
}
