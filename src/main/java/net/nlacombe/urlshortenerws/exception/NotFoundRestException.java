package net.nlacombe.urlshortenerws.exception;

import org.springframework.http.HttpStatus;

public class NotFoundRestException extends RestException
{
	public NotFoundRestException(String message)
	{
		super(HttpStatus.NOT_FOUND, "not-found", message);
	}
}
