package it.macke.blog.web;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

// see: http://stackoverflow.com/questions/34883270/how-to-use-java-time-zoneddatetime-localdatetime-in-pcalendar
@FacesConverter("localDateTimeConverter")
public class LocalDateTimeConverter implements Converter
{
	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object modelValue)
	{
		if (modelValue == null)
		{
			return "";
		}

		if (modelValue instanceof LocalDateTime)
		{
			return getFormatter(context, component)
					.format(ZonedDateTime.of((LocalDateTime) modelValue, ZoneOffset.UTC));
		}
		else
		{
			throw new ConverterException(new FacesMessage(modelValue + " is not a valid LocalDateTime"));
		}
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String submittedValue)
	{
		if (submittedValue == null || submittedValue.isEmpty())
		{
			return null;
		}

		try
		{
			return ZonedDateTime.parse(submittedValue, getFormatter(context, component))
					.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
		}
		catch (final DateTimeParseException e)
		{
			throw new ConverterException(new FacesMessage(submittedValue + " is not a valid local date time"), e);
		}
	}

	private DateTimeFormatter getFormatter(final FacesContext context, final UIComponent component)
	{
		final DateTimeFormatter formatter =
				DateTimeFormatter.ofPattern(getPattern(component), getLocale(context, component));
		final ZoneId zone = getZoneId(component);
		return zone != null ? formatter.withZone(zone) : formatter;
	}

	private String getPattern(final UIComponent component)
	{
		final String pattern = (String) component.getAttributes().get("pattern");

		if (pattern == null)
		{
			throw new IllegalArgumentException("pattern attribute is required");
		}

		return pattern;
	}

	private Locale getLocale(final FacesContext context, final UIComponent component)
	{
		final Object locale = component.getAttributes().get("locale");
		return locale instanceof Locale ? (Locale) locale
				: locale instanceof String ? new Locale((String) locale)
						: context.getViewRoot().getLocale();
	}

	private ZoneId getZoneId(final UIComponent component)
	{
		final Object timeZone = component.getAttributes().get("timeZone");
		return timeZone instanceof TimeZone ? ((TimeZone) timeZone).toZoneId()
				: timeZone instanceof String ? ZoneId.of((String) timeZone)
						: null;
	}
}
