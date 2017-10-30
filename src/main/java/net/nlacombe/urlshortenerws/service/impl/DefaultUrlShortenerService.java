package net.nlacombe.urlshortenerws.service.impl;

import net.nlacombe.urlshortenerws.dto.ShortUrl;
import net.nlacombe.urlshortenerws.entity.ShortUrlEntity;
import net.nlacombe.urlshortenerws.exception.NotFoundRestException;
import net.nlacombe.urlshortenerws.mapper.ShortUrlMapper;
import net.nlacombe.urlshortenerws.repository.ShortUrlRepository;
import net.nlacombe.urlshortenerws.service.UrlShortenerService;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Base64;

@Service
public class DefaultUrlShortenerService implements UrlShortenerService
{
	private static final int BASE64_BITS_PER_CHARACTER = 6;
	private static final int MAX_SHORT_URL_PATH_CHARACTER = 8;
	private static final int BITS_PER_BYTE = 8;

	private ShortUrlRepository shortUrlRepository;
	private ShortUrlMapper shortUrlMapper;

	@Inject
	public DefaultUrlShortenerService(ShortUrlRepository shortUrlRepository, ShortUrlMapper shortUrlMapper)
	{
		this.shortUrlRepository = shortUrlRepository;
		this.shortUrlMapper = shortUrlMapper;
	}

	@Override
	public ShortUrl shorten(String longUrl)
	{
		ShortUrlEntity shortUrlEntity = shortUrlRepository.findByLongUrl(longUrl).orElse(null);

		if (shortUrlEntity == null)
		{
			String shortUrlPath = getShortUrlPath(longUrl);

			shortUrlEntity = new ShortUrlEntity();
			shortUrlEntity.setLongUrl(longUrl);
			shortUrlEntity.setShortUrlPath(shortUrlPath);

			shortUrlEntity = shortUrlRepository.save(shortUrlEntity);
		}

		return shortUrlMapper.mapToDto(shortUrlEntity);
	}

	@Override
	public ShortUrl getShortUrl(String shortUrlPath)
	{
		ShortUrlEntity shortUrlEntity =
				shortUrlRepository.findByShortUrlPath(shortUrlPath)
						.orElseThrow(() -> new NotFoundRestException("Short Url with path \"" + shortUrlPath + "\" not found."));

		return shortUrlMapper.mapToDto(shortUrlEntity);
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
