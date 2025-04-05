package com.url.url_shortener_sb.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.url_shortener_sb.models.UrlMapping;
import com.url.url_shortener_sb.models.User;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
   UrlMapping findByShortUrl(String shortUrl);
   List<UrlMapping> findByUser(User user);
}