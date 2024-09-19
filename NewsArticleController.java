package com.gpt.spring_gpt_4o.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gpt.spring_gpt_4o.dto.NewsArticleDto;
import com.gpt.spring_gpt_4o.entity.NewsArticle;
import com.gpt.spring_gpt_4o.service.NewsArticleService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/news")
public class NewsArticleController {

    private ChatClient chatClient;
    private final NewsArticleService newsArticleService;

    public NewsArticleController(ChatClient.Builder builder, NewsArticleService newsArticleService) {
        this.chatClient = builder.build();
        this.newsArticleService = newsArticleService;
    }

    @GetMapping("/global")
    public ResponseEntity<List<NewsArticle>> getGlobalNews() {
        return new ResponseEntity<>(newsArticleService.getGlobalNews(), HttpStatus.OK);
    }
    @GetMapping("/city/{cityName}")
    public ResponseEntity<List<NewsArticle>> getLocalNews(@PathVariable String cityName) {
        return new ResponseEntity<>(newsArticleService.getLocalNewsForCity(cityName), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<NewsArticle> addNewsArticle(@RequestBody NewsArticleDto newsArticleDto) throws JsonProcessingException {
        return new ResponseEntity<>(newsArticleService.addNewsArticle(newsArticleDto, chatClient), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<NewsArticle> deleteNewsArticle(@RequestParam UUID id) throws JsonProcessingException {
        newsArticleService.deleteNewsArticle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/assign")
    public ResponseEntity assignNewsToCity(@RequestParam UUID id, @RequestParam String cityName) {
        newsArticleService.assignNewsToCity(id, cityName);
        return new ResponseEntity(HttpStatus.OK);
    }


}
