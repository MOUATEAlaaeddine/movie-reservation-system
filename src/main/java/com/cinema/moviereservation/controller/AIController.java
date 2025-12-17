package com.cinema.moviereservation.controller;

import com.cinema.moviereservation.dto.ApiResponse;
import com.cinema.moviereservation.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final OpenAIService openAIService;

    @PostMapping("/recommend")
    public ResponseEntity<ApiResponse<String>> getRecommendations(
            @RequestBody Map<String, String> request) {
        String preferences = request.get("preferences");
        String recommendations = openAIService.generateMovieRecommendation(preferences);
        return ResponseEntity.ok(
                ApiResponse.success("Recommendations generated", recommendations)
        );
    }

    @PostMapping("/describe")
    public ResponseEntity<ApiResponse<String>> describeMovie(
            @RequestBody Map<String, String> request) {
        String movieTitle = request.get("title");
        String description = openAIService.generateMovieDescription(movieTitle);
        return ResponseEntity.ok(
                ApiResponse.success("Description generated", description)
        );
    }
}