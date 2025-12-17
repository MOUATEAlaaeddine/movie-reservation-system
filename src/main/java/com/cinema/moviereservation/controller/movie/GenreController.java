package com.cinema.moviereservation.controller.movie;

import com.cinema.moviereservation.dto.ApiResponse;
import com.cinema.moviereservation.dto.GenreDTO;
import com.cinema.moviereservation.service.GenreService;
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
    public ResponseEntity<ApiResponse<GenreDTO>> createGenre(@Valid @RequestBody GenreDTO genreDTO) {
        try {
            GenreDTO created = genreService.createGenre(genreDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Genre created successfully", created));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GenreDTO>>> getAllGenres() {
        List<GenreDTO> genres = genreService.getAllGenres();
        return ResponseEntity.ok(ApiResponse.success("Genres retrieved successfully", genres));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GenreDTO>> getGenreById(@PathVariable Long id) {
        try {
            GenreDTO genre = genreService.getGenreById(id);
            return ResponseEntity.ok(ApiResponse.success("Genre found", genre));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GenreDTO>> updateGenre(
            @PathVariable Long id,
            @Valid @RequestBody GenreDTO genreDTO) {
        try {
            GenreDTO updated = genreService.updateGenre(id, genreDTO);
            return ResponseEntity.ok(ApiResponse.success("Genre updated successfully", updated));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGenre(@PathVariable Long id) {
        try {
            genreService.deleteGenre(id);
            return ResponseEntity.ok(ApiResponse.success("Genre deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}