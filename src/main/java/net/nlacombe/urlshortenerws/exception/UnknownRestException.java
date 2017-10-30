package net.nlacombe.urlshortenerws.exception;

import org.springframework.http.HttpStatus;

public class UnknownRestException extends RestException
{
	public UnknownRestException(Throwable cause)
	{
		super(HttpStatus.INTERNAL_SERVER_ERROR, "unknown-error", "Unknown error", cause);
	}
}
