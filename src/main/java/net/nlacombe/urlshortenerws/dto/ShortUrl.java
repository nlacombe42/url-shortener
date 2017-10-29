package net.nlacombe.urlshortenerws.dto;

public class ShortUrl
{
	private int shortUrlId;
	private String shortUrlPath;
	private String shortUrl;
	private String longUrl;

	public int getShortUrlId()
	{
		return shortUrlId;
	}

	public void setShortUrlId(int shortUrlId)
	{
		this.shortUrlId = shortUrlId;
	}

	public String getShortUrlPath()
	{
		return shortUrlPath;
	}

	public void setShortUrlPath(String shortUrlPath)
	{
		this.shortUrlPath = shortUrlPath;
	}

	public String getShortUrl()
	{
		return shortUrl;
	}

	public void setShortUrl(String shortUrl)
	{
		this.shortUrl = shortUrl;
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
