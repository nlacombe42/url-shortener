package net.nlacombe.urlshortenerws.webservice;

import net.nlacombe.urlshortenerws.dto.ShortUrl;
import net.nlacombe.urlshortenerws.dto.ShortUrlCreationRequest;
import net.nlacombe.urlshortenerws.service.UrlShortenerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@Transactional
public class UrlShortenerWebService
{
	private UrlShortenerService urlShortenerService;

	@Inject
	public UrlShortenerWebService(UrlShortenerService urlShortenerService)
	{
		this.urlShortenerService = urlShortenerService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/r/{shortUrlPath}")
	public ResponseEntity redirectToLongUrl(@PathVariable String shortUrlPath)
	{
		ShortUrl shortUrl = urlShortenerService.getShortUrl(shortUrlPath);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.LOCATION, shortUrl.getLongUrl());

		return new ResponseEntity(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/short-urls")
	public ShortUrl shorten(@RequestBody ShortUrlCreationRequest shortUrlCreationRequest)
	{
		return urlShortenerService.shorten(shortUrlCreationRequest.getLongUrl());
	}
}
