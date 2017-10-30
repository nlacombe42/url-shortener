package net.nlacombe.urlshortenerws.service.impl;

import net.nlacombe.urlshortenerws.dto.ShortUrl;
import net.nlacombe.urlshortenerws.entity.ShortUrlEntity;
import net.nlacombe.urlshortenerws.exception.NotFoundRestException;
import net.nlacombe.urlshortenerws.mapper.ShortUrlMapper;
import net.nlacombe.urlshortenerws.repository.ShortUrlRepository;
import net.nlacombe.urlshortenerws.test.AnswerFirstArgument;
import net.nlacombe.urlshortenerws.test.constants.TestConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUrlShortenerServiceTest
{
	@InjectMocks
	private DefaultUrlShortenerService defaultUrlShortenerService;

	@Mock
	private ShortUrlRepository shortUrlRepository;

	@Spy
	private ShortUrlMapper shortUrlMapper;

	@Test
	public void short_url_start_with_clip_absolute_url()
	{
		String longUrl = "https://google.ca";

		givenShortUrlNotAlreadyExisting(longUrl);
		givenSucessfulSave();

		ShortUrl shortUrl = defaultUrlShortenerService.shorten(longUrl);

		assertThat(shortUrl).isNotNull();
		assertThat(shortUrl.getShortUrl()).startsWith(TestConstants.CLIP_ABSOLUTE_URL);
	}

	@Test
	public void short_url_max_absolute_path_of_11_characters()
	{
		int absoluteUrlMaxLength = TestConstants.CLIP_ABSOLUTE_URL.length() + 10;
		String longUrl = "https://google.ca";

		givenShortUrlNotAlreadyExisting(longUrl);
		givenSucessfulSave();

		ShortUrl shortUrl = defaultUrlShortenerService.shorten(longUrl);

		assertThat(shortUrl).isNotNull();
		assertThat(shortUrl.getShortUrl().length()).isLessThanOrEqualTo(absoluteUrlMaxLength);
	}

	@Test
	public void same_short_url_for_same_long_url()
	{
		String longUrl = "https://nlacombe.net/";

		givenShortUrlNotAlreadyExisting(longUrl);
		givenSucessfulSave();

		ShortUrl first_shortUrl = defaultUrlShortenerService.shorten(longUrl);
		ShortUrl second_shortUrl = defaultUrlShortenerService.shorten(longUrl);

		assertThat(first_shortUrl).isNotNull();
		assertThat(second_shortUrl).isNotNull();
		assertThat(first_shortUrl.getShortUrl()).isEqualTo(second_shortUrl.getShortUrl());
	}

	@Test
	public void different_long_url_shorten_to_different_short_url()
	{
		String longUrl1 = "https://nlacombe.net/";
		String longUrl2 = "https://google.ca/";

		givenShortUrlNotAlreadyExisting(longUrl1);
		givenShortUrlNotAlreadyExisting(longUrl2);
		givenSucessfulSave();

		ShortUrl first_shortUrl = defaultUrlShortenerService.shorten(longUrl1);
		ShortUrl second_shortUrl = defaultUrlShortenerService.shorten(longUrl2);

		assertThat(first_shortUrl).isNotNull();
		assertThat(second_shortUrl).isNotNull();
		assertThat(first_shortUrl.getShortUrl()).isNotEqualTo(second_shortUrl.getShortUrl());
	}

	@Test
	public void long_url_returned_should_be_the_same_as_long_url_provided()
	{
		String longUrl = "https://nlacombe.net/";

		givenShortUrlNotAlreadyExisting(longUrl);
		givenSucessfulSave();

		ShortUrl shortUrl = defaultUrlShortenerService.shorten(longUrl);

		assertThat(shortUrl).isNotNull();
		assertThat(shortUrl.getLongUrl()).isEqualTo(longUrl);
	}

	@Test
	public void return_long_url_from_short_url_path()
	{
		String providedShortUrlPath = "AAAAAAAA";
		String providedLongUrl = "https//google.ca";

		when(shortUrlRepository.findByShortUrlPath(providedShortUrlPath)).thenReturn(Optional.of(new ShortUrlEntity(providedShortUrlPath, providedLongUrl)));

		ShortUrl shortUrl = defaultUrlShortenerService.getShortUrl(providedShortUrlPath);

		assertThat(shortUrl).isNotNull();
		assertThat(shortUrl.getLongUrl()).isEqualTo(providedLongUrl);
	}

	@Test(expected = NotFoundRestException.class)
	public void get_short_url_should_throw_error_on_non_exitant_short_url()
	{
		String providedShortUrlPath = "AAAAAAAA";

		when(shortUrlRepository.findByShortUrlPath(providedShortUrlPath)).thenReturn(Optional.empty());

		defaultUrlShortenerService.getShortUrl(providedShortUrlPath);
	}

	private void givenSucessfulSave()
	{
		when(shortUrlRepository.save(any(ShortUrlEntity.class))).then(new AnswerFirstArgument());
	}

	private void givenShortUrlNotAlreadyExisting(String longUrl)
	{
		when(shortUrlRepository.findByLongUrl(longUrl)).thenReturn(Optional.empty());
	}
}