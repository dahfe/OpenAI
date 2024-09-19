package com.gpt.spring_gpt_4o.repository;

import com.gpt.spring_gpt_4o.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, UUID> {
    List<NewsArticle> findByCityId(UUID cityId);
    List<NewsArticle> findByIsGlobal(boolean isGlobal);
    NewsArticle findByTitle(String title);
}
