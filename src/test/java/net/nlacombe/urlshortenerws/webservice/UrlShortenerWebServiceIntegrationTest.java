package net.nlacombe.urlshortenerws.webservice;

import net.nlacombe.urlshortenerws.dto.ShortUrl;
import net.nlacombe.urlshortenerws.dto.ShortUrlCreationRequest;
import net.nlacombe.urlshortenerws.exception.RestExceptionBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlShortenerWebServiceIntegrationTest
{
	private static final String CLIP_ABSOLUTE_URL = "http://cl.ip/";
	private static final URI SHORTEN_URL_ENDPOINT_URI = URI.create("/short-urls/");
	private static final String REDIRECT_ENDPOINT_URL = "/r/";

	@Inject
	private TestRestTemplate restTemplate;

	@Test
	public void short_url_start_with_clip_absolute_url()
	{
		ShortUrlCreationRequest shortUrlCreationRequest = new ShortUrlCreationRequest("https://nlacombe.net/");

		ShortUrl shortUrl = restTemplate.postForObject(SHORTEN_URL_ENDPOINT_URI, shortUrlCreationRequest, ShortUrl.class);

		assertThat(shortUrl).isNotNull();
		assertThat(shortUrl.getShortUrl()).startsWith(CLIP_ABSOLUTE_URL);
	}

	@Test
	public void short_url_max_absolute_path_of_11_characters()
	{
		int absoluteUrlMaxLength = CLIP_ABSOLUTE_URL.length() + 10;
		ShortUrlCreationRequest shortUrlCreationRequest = new ShortUrlCreationRequest("https://nlacombe.net/");

		ShortUrl shortUrl = restTemplate.postForObject(SHORTEN_URL_ENDPOINT_URI, shortUrlCreationRequest, ShortUrl.class);

		assertThat(shortUrl).isNotNull();
		assertThat(shortUrl.getShortUrl()).isNotNull();
		assertThat(shortUrl.getShortUrl().length()).isLessThanOrEqualTo(absoluteUrlMaxLength);
	}

	@Test
	public void same_short_url_for_same_long_url()
	{
		String longUrl = "https://nlacombe.net/";
		ShortUrlCreationRequest shortUrlCreationRequest = new ShortUrlCreationRequest(longUrl);

		ShortUrl first_shortUrl = restTemplate.postForObject(SHORTEN_URL_ENDPOINT_URI, shortUrlCreationRequest, ShortUrl.class);
		ShortUrl second_shortUrl = restTemplate.postForObject(SHORTEN_URL_ENDPOINT_URI, shortUrlCreationRequest, ShortUrl.class);

		assertThat(first_shortUrl).isNotNull();
		assertThat(second_shortUrl).isNotNull();
		assertThat(first_shortUrl.getShortUrl()).isEqualTo(second_shortUrl.getShortUrl());
	}

	@Test
	public void different_long_url_shorten_to_different_short_url()
	{
		ShortUrlCreationRequest shortUrlCreationRequest1 = new ShortUrlCreationRequest("https://nlacombe.net/");
		ShortUrlCreationRequest shortUrlCreationRequest2 = new ShortUrlCreationRequest("https://google.com");

		ShortUrl shortUrl1 = restTemplate.postForObject(SHORTEN_URL_ENDPOINT_URI, shortUrlCreationRequest1, ShortUrl.class);
		ShortUrl shortUrl2 = restTemplate.postForObject(SHORTEN_URL_ENDPOINT_URI, shortUrlCreationRequest2, ShortUrl.class);

		assertThat(shortUrl1).isNotNull();
		assertThat(shortUrl2).isNotNull();
		assertThat(shortUrl1.getShortUrl()).isNotEqualTo(shortUrl2.getShortUrl());
	}

	@Test
	public void long_url_returned_should_be_the_same_as_long_url_provided()
	{
		ShortUrlCreationRequest shortUrlCreationRequest = new ShortUrlCreationRequest("https://nlacombe.net/");

		ShortUrl shortUrl = restTemplate.postForObject(SHORTEN_URL_ENDPOINT_URI, shortUrlCreationRequest, ShortUrl.class);

		assertThat(shortUrl).isNotNull();
		assertThat(shortUrl.getLongUrl()).isEqualTo(shortUrlCreationRequest.getLongUrl());
	}

	@Test
	public void redirect_should_redirect_to_long_url_of_shortened_url()
	{
		ShortUrlCreationRequest shortUrlCreationRequest = new ShortUrlCreationRequest("https://nlacombe.net/");

		ShortUrl shortUrl = restTemplate.postForObject(SHORTEN_URL_ENDPOINT_URI, shortUrlCreationRequest, ShortUrl.class);
		URI redirectEndpointUri = URI.create(REDIRECT_ENDPOINT_URL + shortUrl.getShortUrlPath());

		ResponseEntity redirectResponse = restTemplate.getForEntity(redirectEndpointUri, null);

		assertThat(redirectResponse.getHeaders().get(HttpHeaders.LOCATION)).hasSize(1);
		assertThat(redirectResponse.getHeaders().get(HttpHeaders.LOCATION).get(0)).isEqualTo(shortUrlCreationRequest.getLongUrl());
	}

	@Test
	public void redirect_should_return_http_status_401()
	{
		ShortUrlCreationRequest shortUrlCreationRequest = new ShortUrlCreationRequest("https://nlacombe.net/");

		ShortUrl shortUrl = restTemplate.postForObject(SHORTEN_URL_ENDPOINT_URI, shortUrlCreationRequest, ShortUrl.class);
		URI redirectEndpointUri = URI.create(REDIRECT_ENDPOINT_URL + shortUrl.getShortUrlPath());

		ResponseEntity redirectResponse = restTemplate.getForEntity(redirectEndpointUri, null);

		assertThat(redirectResponse.getStatusCode()).isEqualTo(HttpStatus.MOVED_PERMANENTLY);
	}

	@Test
	public void redirect_should_return_error_if_short_url_does_not_exists()
	{
		String nonExistingShortUrlPath = "AAAAAAAA";

		URI redirectEndpointUri = URI.create(REDIRECT_ENDPOINT_URL + nonExistingShortUrlPath);

		ResponseEntity<RestExceptionBody> errorResponse = restTemplate.getForEntity(redirectEndpointUri, RestExceptionBody.class);

		assertThat(errorResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(errorResponse.getBody()).isNotNull();
		assertThat(errorResponse.getBody().getCode()).isEqualTo("not-found");
	}
}