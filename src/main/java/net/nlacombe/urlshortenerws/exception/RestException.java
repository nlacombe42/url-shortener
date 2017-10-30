package net.nlacombe.urlshortenerws.exception;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException
{
	private HttpStatus httpStatus;
	private String code;

	public RestException(HttpStatus httpStatus, String code, String message, Throwable cause)
	{
		super(message, cause);

		this.httpStatus = httpStatus;
		this.code = code;
	}

	public RestException(HttpStatus httpStatus, String code, String message)
	{
		super(message);

		this.httpStatus = httpStatus;
		this.code = code;
	}

	public HttpStatus getHttpStatus()
	{
		return httpStatus;
	}

	public String getCode()
	{
		return code;
	}
}
