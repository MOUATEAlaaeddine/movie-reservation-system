package com.cinema.moviereservation.controller.movie;

import com.cinema.moviereservation.dto.ApiResponse;
import com.cinema.moviereservation.dto.request.MovieRequest;
import com.cinema.moviereservation.dto.response.MovieResponse;
import com.cinema.moviereservation.service.movie.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(
            @Valid @RequestBody MovieRequest request) {
        MovieResponse response = movieService.createMovie(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Movie created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getAllMovies() {
        List<MovieResponse> movies = movieService.getAllMovies();
        return ResponseEntity.ok(
                ApiResponse.success("Movies retrieved successfully", movies)
        );
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getActiveMovies() {
        List<MovieResponse> movies = movieService.getActiveMovies();
        return ResponseEntity.ok(
                ApiResponse.success("Active movies retrieved", movies)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> searchMovies(
            @RequestParam String title) {
        List<MovieResponse> movies = movieService.searchMovies(title);
        return ResponseEntity.ok(
                ApiResponse.success("Search results", movies)
        );
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getMoviesByGenre(
            @PathVariable Long genreId) {
        List<MovieResponse> movies = movieService.getMoviesByGenre(genreId);
        return ResponseEntity.ok(
                ApiResponse.success("Movies by genre retrieved", movies)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieRequest request) {
        MovieResponse response = movieService.updateMovie(id, request);
        return ResponseEntity.ok(
                ApiResponse.success("Movie updated successfully", response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(
                ApiResponse.success("Movie deleted successfully", null)
        );
    }
}