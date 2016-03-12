package it.macke.blog.web;

import java.util.Map;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class Controller
{
	protected Optional<String> getParameter(final String name)
	{
		final FacesContext context = FacesContext.getCurrentInstance();
		final ExternalContext externalContext = context.getExternalContext();
		final Map<String, String> params = externalContext.getRequestParameterMap();
		return Optional.of(params.get("id"));
	}

	protected void addMessage(final String message)
	{
		final FacesContext context = FacesContext.getCurrentInstance();
		final ExternalContext externalContext = context.getExternalContext();
		context.addMessage(null, new FacesMessage(message));
		externalContext.getFlash().setKeepMessages(true);
	}

	protected String redirect(final String page)
	{
		final String separator = page.contains("?") ? "&" : "?";
		return page + separator + "faces-redirect=true";
	}
}
