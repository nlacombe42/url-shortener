package net.nlacombe.urlshortenerws.service.impl;

import net.nlacombe.urlshortenerws.constants.Constants;
import net.nlacombe.urlshortenerws.dto.ShortUrl;
import net.nlacombe.urlshortenerws.service.UrlShortenerService;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class DefaultUrlShortenerService implements UrlShortenerService
{
	private static final int BASE64_BITS_PER_CHARACTER = 6;
	private static final int MAX_SHORT_URL_PATH_CHARACTER = 8;
	private static final int BITS_PER_BYTE = 8;

	@Override
	public ShortUrl shorten(String longUrl)
	{
		String shortUrlPath = getShortUrlPath(longUrl);

		ShortUrl shortUrl = new ShortUrl();
		shortUrl.setLongUrl(longUrl);
		shortUrl.setShortUrlPath(shortUrlPath);
		shortUrl.setShortUrl(Constants.CLIP_REDIRECT_ABSOLUTE_URL + shortUrlPath);

		return shortUrl;
	}

	private String getShortUrlPath(String longUrl)
	{
		int maxShortUrlHashBytes = MAX_SHORT_URL_PATH_CHARACTER * BASE64_BITS_PER_CHARACTER / BITS_PER_BYTE;
		byte[] longUrlHash = hashLongUrl(longUrl);

		longUrlHash = ArrayUtils.subarray(longUrlHash, 0, maxShortUrlHashBytes);

		return Base64.getEncoder().encodeToString(longUrlHash);
	}

	private byte[] hashLongUrl(String longUrl)
	{
		SHA3.DigestSHA3 digestSHA3 = new SHA3.DigestSHA3(224);

		return digestSHA3.digest(longUrl.getBytes());
	}
}
