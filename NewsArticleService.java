package com.gpt.spring_gpt_4o.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gpt.spring_gpt_4o.dto.NewsArticleDto;
import com.gpt.spring_gpt_4o.entity.NewsArticle;
import org.springframework.ai.chat.client.ChatClient;

import java.util.List;
import java.util.UUID;

public interface NewsArticleService {
    public List<NewsArticle> getGlobalNews();
    public List<NewsArticle> getLocalNewsForCity(String cityName);
    public NewsArticle addNewsArticle(NewsArticleDto newsArticleDto, ChatClient chatClient) throws JsonProcessingException;
    public void assignNewsToCity(UUID id, String cityName);
    public void deleteNewsArticle(UUID id);

}
