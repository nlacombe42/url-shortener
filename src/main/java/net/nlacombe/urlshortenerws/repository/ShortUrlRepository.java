package net.nlacombe.urlshortenerws.repository;

import net.nlacombe.urlshortenerws.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Integer>
{
	Optional<ShortUrlEntity> findByShortUrlPath(String shortUrlPath);
	Optional<ShortUrlEntity> findByLongUrl(String shortUrlPath);
}
