package it.macke.blog.web;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import it.macke.blog.domain.Post;
import it.macke.blog.persistence.PostRepository;

@Named
@RequestScoped
public class PostController extends Controller
{
	public static final String POSTS_PAGE = "index.xhtml";
	public static final String POST_PAGE = "post.xhtml";

	@Inject
	private PostRepository repo;

	private Optional<Post> currentPost;

	public String getPostId()
	{
		final Optional<String> idOrNothing = getParameter("id");
		return idOrNothing.isPresent()
				? idOrNothing.get()
				: "";
	}

	public String getPostsPage()
	{
		return POSTS_PAGE;
	}

	public String getPostPage()
	{
		return POST_PAGE;
	}

	public Iterable<Post> getPosts()
	{
		return repo.findAll();
	}

	public Optional<Long> parsePostId()
	{
		try
		{
			return Optional.of(Long.parseLong(getPostId()));
		}
		catch (final NumberFormatException nfe)
		{
			addMessage(FacesMessage.SEVERITY_ERROR, "Invalid post ID.");
			return Optional.empty();
		}
	}

	public String checkWhetherPostExists()
	{
		findCurrentPost();
		if (!currentPost.isPresent())
		{
			addMessage(FacesMessage.SEVERITY_ERROR, "Post not found.");
			return getPostsPage();
		}
		return null;
	}

	private void findCurrentPost()
	{
		if (currentPost != null)
		{
			return;
		}
		currentPost = Optional.empty();
		final Optional<Long> postIdOrNothing = parsePostId();
		if (postIdOrNothing.isPresent())
		{
			currentPost = repo.find(postIdOrNothing.get());
		}
	}

	public Post getCurrentPost()
	{
		findCurrentPost();
		if (currentPost == null || !currentPost.isPresent())
		{
			return new Post("Post not found", "No content");
		}
		return currentPost.get();
	}
}
