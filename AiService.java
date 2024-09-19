package com.gpt.spring_gpt_4o.service;

import com.gpt.spring_gpt_4o.chatTemplates.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class AiService {

    public IsGlobal isGlobal(String content, ChatClient chatClient) {

        BeanOutputConverter<IsGlobal> converter
                = new BeanOutputConverter<>(IsGlobal.class);

        String message = """
                Form the local or global news of {content}.s
                Include the content as a key and the local or global as its value
                {format}
                """;


        PromptTemplate template
                = new PromptTemplate(message);

        Prompt prompt = template
                .create(Map.of("content",content, "format", converter.getFormat()));

        ChatResponse response = chatClient
                .prompt(prompt)
                .call()
                .chatResponse();

        return converter.convert(response.getResult().getOutput().getContent());
    }

    public CityAi determineCity(String content, ChatClient chatClient) {

        BeanOutputConverter<CityAi> converter
                = new BeanOutputConverter<>(CityAi.class);

        String message = """
                Form the city from {content}.s
                Include the content as a key and the city as its value
                {format}
                """;


        PromptTemplate template
                = new PromptTemplate(message);

        Prompt prompt = template
                .create(Map.of("content",content, "format", converter.getFormat()));

        ChatResponse response = chatClient
                .prompt(prompt)
                .call()
                .chatResponse();

        return converter.convert(response.getResult().getOutput().getContent());
    }

}
