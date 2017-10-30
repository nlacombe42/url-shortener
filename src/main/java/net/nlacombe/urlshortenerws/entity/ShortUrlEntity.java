package net.nlacombe.urlshortenerws.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "short_url")
public class ShortUrlEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shortUrlId;

	private String shortUrlPath;
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

	public String getLongUrl()
	{
		return longUrl;
	}

	public void setLongUrl(String longUrl)
	{
		this.longUrl = longUrl;
	}
}
