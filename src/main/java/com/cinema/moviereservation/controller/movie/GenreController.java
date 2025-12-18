package com.cinema.moviereservation.controller.movie;

import com.cinema.moviereservation.dto.ApiResponse;
import com.cinema.moviereservation.dto.request.GenreRequest;
import com.cinema.moviereservation.dto.response.GenreResponse;
import com.cinema.moviereservation.service.movie.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<ApiResponse<GenreResponse>> createGenre(
            @Valid @RequestBody GenreRequest request) {
        GenreResponse response = genreService.createGenre(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Genre created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GenreResponse>>> getAllGenres() {
        List<GenreResponse> genres = genreService.getAllGenres();
        return ResponseEntity.ok(
                ApiResponse.success("Genres retrieved successfully", genres)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GenreResponse>> getGenreById(@PathVariable Long id) {
        GenreResponse genre = genreService.getGenreById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Genre found", genre)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GenreResponse>> updateGenre(
            @PathVariable Long id,
            @Valid @RequestBody GenreRequest request) {
        GenreResponse response = genreService.updateGenre(id, request);
        return ResponseEntity.ok(
                ApiResponse.success("Genre updated successfully", response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok(
                ApiResponse.success("Genre deleted successfully", null)
        );
    }
}