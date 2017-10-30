package net.nlacombe.urlshortenerws.exceptionhandler;

import net.nlacombe.urlshortenerws.exception.RestException;
import net.nlacombe.urlshortenerws.exception.RestExceptionBody;
import net.nlacombe.urlshortenerws.exception.UnknownRestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler
	private ResponseEntity<RestExceptionBody> handleRestException(RestException restException)
	{
		return toResponse(restException);
	}

	@ExceptionHandler
	private ResponseEntity<RestExceptionBody> handleUnkownThrowable(Throwable throwable)
	{
		logger.error("Uncaught exception.", throwable);

		return toResponse(new UnknownRestException(throwable));
	}

	private ResponseEntity<RestExceptionBody> toResponse(RestException restException)
	{
		return new ResponseEntity<>(new RestExceptionBody(restException.getCode(), restException.getMessage()), restException.getHttpStatus());
	}
}
