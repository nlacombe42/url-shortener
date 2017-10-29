package net.nlacombe.urlshortenerws.dto;

public class ShortUrlCreationRequest
{
	private String longUrl;

	public ShortUrlCreationRequest()
	{
	}

	public ShortUrlCreationRequest(String longUrl)
	{
		this.longUrl = longUrl;
	}

	public String getLongUrl()
	{
		return longUrl;
	}

	public void setLongUrl(String longUrl)
	{
		this.longUrl = longUrl;
	}
}
