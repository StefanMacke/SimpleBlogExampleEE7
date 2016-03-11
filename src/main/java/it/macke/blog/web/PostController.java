package it.macke.blog.web;

import javax.enterprise.context.RequestScoped;
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
}
