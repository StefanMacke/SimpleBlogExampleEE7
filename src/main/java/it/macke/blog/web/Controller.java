package it.macke.blog.web;

import java.util.Map;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public abstract class Controller
{
	protected Optional<String> getParameter(final String name)
	{
		final Map<String, String> params =
				FacesContext.getCurrentInstance()
						.getExternalContext()
						.getRequestParameterMap();
		return Optional.ofNullable(params.get(name));
	}

	protected void addMessage(final Severity severity, final String message)
	{
		final FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(
				null, new FacesMessage(severity, message, null));
		context.getExternalContext().getFlash().setKeepMessages(true);
	}

	protected String redirect(final String page)
	{
		final String separator = page.contains("?") ? "&" : "?";
		return page + separator + "faces-redirect=true&includeViewParams=true";
	}
}
