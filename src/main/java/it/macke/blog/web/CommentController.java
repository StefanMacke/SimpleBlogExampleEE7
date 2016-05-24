package it.macke.blog.web;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.macke.blog.domain.Comment;
import it.macke.blog.domain.Post;
import it.macke.blog.persistence.CommentRepository;

@Named
@RequestScoped
public class CommentController extends Controller
{
	@Inject
	private CommentRepository repo;

	@NotNull(message = "Please enter a comment")
	@Size(min = 5, message = "Please enter a valid comment")
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
		repo.persist(p.getId(), new Comment(getContent()));
		addMessage(FacesMessage.SEVERITY_INFO, "Your comment has beed saved");
		return redirect(PostController.POST_PAGE + "?id=" + p.getId());
	}
}
