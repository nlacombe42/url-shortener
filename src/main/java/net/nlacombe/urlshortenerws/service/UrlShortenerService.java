package net.nlacombe.urlshortenerws.service;

import net.nlacombe.urlshortenerws.dto.ShortUrl;

public interface UrlShortenerService
{
	ShortUrl shorten(String longUrl);

	ShortUrl getShortUrl(String shortUrlPath);
}
