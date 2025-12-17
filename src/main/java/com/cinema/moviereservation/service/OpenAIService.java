package com.cinema.moviereservation.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    public String generateMovieRecommendation(String userPreferences) {
        try {
            OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(30));

            ChatMessage systemMessage = new ChatMessage(
                    "system",
                    "You are a movie recommendation expert. Provide personalized movie suggestions."
            );

            ChatMessage userMessage = new ChatMessage(
                    "user",
                    "Based on these preferences: " + userPreferences +
                            ", recommend 3 movies with brief descriptions."
            );

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(List.of(systemMessage, userMessage))
                    .maxTokens(500)
                    .build();

            return service.createChatCompletion(request)
                    .getChoices()
                    .get(0)
                    .getMessage()
                    .getContent();

        } catch (Exception e) {
            return "Error generating recommendations: " + e.getMessage();
        }
    }

    public String generateMovieDescription(String movieTitle) {
        try {
            OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(30));

            ChatMessage message = new ChatMessage(
                    "user",
                    "Write a compelling 2-sentence description for the movie: " + movieTitle
            );

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(List.of(message))
                    .maxTokens(100)
                    .build();

            return service.createChatCompletion(request)
                    .getChoices()
                    .get(0)
                    .getMessage()
                    .getContent();

        } catch (Exception e) {
            return "Classic movie - description unavailable";
        }
    }
}