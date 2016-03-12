package it.macke.blog.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.macke.blog.domain.Comment;
import it.macke.blog.domain.Post;
import it.macke.blog.persistence.CommentService;

@Named
@RequestScoped
public class CommentController extends Controller
{
	@Inject
	private CommentService service;

	private String content;

	public String getContent()
	{
		return content;
	}

	public void setContent(final String content)
	{
		this.content = content;
	}

	public String save(final Post p)
	{
		service.persist(p.getId(), new Comment(getContent()));
		addMessage("Your comment has beed saved");
		return redirect("post.xhtml?id=" + p.getId());
	}
}
