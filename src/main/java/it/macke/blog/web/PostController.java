package it.macke.blog.web;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.macke.blog.domain.Post;
import it.macke.blog.persistence.PostService;

@Named
@RequestScoped
public class PostController
{
	@Inject
	private PostService service;

	public Iterable<Post> getPosts()
	{
		return service.findAll();
	}

	public Post getCurrentPost()
	{
		final FacesContext context = FacesContext.getCurrentInstance();
		final ExternalContext extContext = context.getExternalContext();
		final Map<String, String> params = extContext.getRequestParameterMap();
		final Long id = Long.parseLong(params.get("id"));
		final Post p = service.find(id);
		if (p == null)
		{
			final FacesMessage message = new FacesMessage("Post not found");
			context.addMessage(null, message);
		}
		return p;
	}
}
