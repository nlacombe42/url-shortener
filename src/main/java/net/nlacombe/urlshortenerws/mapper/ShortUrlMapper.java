package net.nlacombe.urlshortenerws.mapper;

import net.nlacombe.urlshortenerws.constants.Constants;
import net.nlacombe.urlshortenerws.dto.ShortUrl;
import net.nlacombe.urlshortenerws.entity.ShortUrlEntity;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlMapper extends BeanMapper<ShortUrl, ShortUrlEntity>
{
	public ShortUrlMapper()
	{
		super(ShortUrl.class, ShortUrlEntity.class);
	}

	@Override
	public ShortUrl mapToDto(ShortUrlEntity shortUrlEntity)
	{
		ShortUrl shortUrl = super.mapToDto(shortUrlEntity);
		shortUrl.setShortUrl(Constants.CLIP_REDIRECT_ABSOLUTE_URL + shortUrl.getShortUrlPath());

		return shortUrl;
	}
}
