package com.gpt.spring_gpt_4o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gpt.spring_gpt_4o.chatTemplates.CityAi;
import com.gpt.spring_gpt_4o.chatTemplates.IsGlobal;
import com.gpt.spring_gpt_4o.dto.NewsArticleDto;
import com.gpt.spring_gpt_4o.entity.City;
import com.gpt.spring_gpt_4o.entity.NewsArticle;
import com.gpt.spring_gpt_4o.exception.ModelExistsException;
import com.gpt.spring_gpt_4o.repository.CityRepository;
import com.gpt.spring_gpt_4o.repository.NewsArticleRepository;
import com.gpt.spring_gpt_4o.service.AiService;
import com.gpt.spring_gpt_4o.service.NewsArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class NewsArticleServiceImpl implements NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final CityRepository cityRepository;
    protected final AiService aiService;

    @Override
    public List<NewsArticle> getGlobalNews() {
        return newsArticleRepository.findByIsGlobal(true);
    }

    @Override
    public List<NewsArticle> getLocalNewsForCity(String cityName) {
        City city = cityRepository.findByNameContainingIgnoreCase(cityName).stream().findFirst().orElse(null);
        if (city == null) {
            throw new ModelExistsException("City doesn't exists");
        }
        return newsArticleRepository.findByCityId(city.getId());
    }

    @Override
    public NewsArticle addNewsArticle(NewsArticleDto newsArticleDto, ChatClient chatClient) throws JsonProcessingException {
        NewsArticle newsArticle = new NewsArticle();
        settingNewsArticle(newsArticleDto, newsArticle, chatClient);
        return newsArticleRepository.save(newsArticle);
    }

    @Override
    public void assignNewsToCity(UUID id, String cityName) {

        if (newsArticleRepository.findById(id).isEmpty()) {
            throw new ModelExistsException("No article with the title found.");
        }
        NewsArticle newsArticle = newsArticleRepository.findById(id).get();
        City city = cityRepository.findByNameContainingIgnoreCase(cityName).stream().findFirst().orElse(null);
        if (city != null) {
            newsArticle.setCity(city);
            newsArticle.setGlobal(false);
        }
        else {
            newsArticle.setGlobal(true);
        }
        newsArticleRepository.save(newsArticle);
    }

    @Override
    public void deleteNewsArticle(UUID id) {
        newsArticleRepository.deleteById(id);
    }

    private void settingNewsArticle(NewsArticleDto newsArticleDto, NewsArticle newsArticle, ChatClient chatClient) throws JsonProcessingException {
        newsArticle.setTitle(newsArticleDto.getTitle());
        newsArticle.setContent(newsArticleDto.getContent());
        IsGlobal result = aiService.isGlobal(newsArticle.getContent(), chatClient);
        if (result.isGlobal().get(0).toLowerCase().contains("local")) {
            CityAi city = aiService.determineCity(newsArticle.getContent(), chatClient);
            newsArticle.setCity(cityRepository.findByName(city.city().get(0)).orElseThrow());
            newsArticle.setGlobal(false);
        } else {
            newsArticle.setGlobal(true);
        }
    }
}
