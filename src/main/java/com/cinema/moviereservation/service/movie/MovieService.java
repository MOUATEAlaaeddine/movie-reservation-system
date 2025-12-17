package com.cinema.moviereservation.service.movie;

import com.cinema.moviereservation.dto.request.MovieRequest;
import com.cinema.moviereservation.dto.response.MovieResponse;
import com.cinema.moviereservation.entity.Genre;
import com.cinema.moviereservation.entity.Movie;
import com.cinema.moviereservation.exception.ResourceNotFoundException;
import com.cinema.moviereservation.repository.GenreRepository;
import com.cinema.moviereservation.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public MovieResponse createMovie(MovieRequest request) {
        log.info("Creating new movie: {}", request.getTitle());

        Genre genre = genreRepository.findById(request.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", request.getGenreId()));

        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setGenre(genre);
        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setRating(request.getRating() != null ? request.getRating() : 0.0);
        movie.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

        Movie savedMovie = movieRepository.save(movie);
        log.info("Movie created successfully with ID: {}", savedMovie.getId());

        return mapToResponse(savedMovie);
    }

    public List<MovieResponse> getAllMovies() {
        log.info("Fetching all movies");
        return movieRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MovieResponse> getActiveMovies() {
        log.info("Fetching active movies only");
        return movieRepository.findByIsActiveTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MovieResponse getMovieById(Long id) {
        log.info("Fetching movie with ID: {}", id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        return mapToResponse(movie);
    }

    public List<MovieResponse> searchMovies(String title) {
        log.info("Searching movies with title containing: {}", title);
        return movieRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MovieResponse> getMoviesByGenre(Long genreId) {
        log.info("Fetching movies for genre ID: {}", genreId);

        // Verify genre exists
        if (!genreRepository.existsById(genreId)) {
            throw new ResourceNotFoundException("Genre", "id", genreId);
        }

        return movieRepository.findByGenreId(genreId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MovieResponse updateMovie(Long id, MovieRequest request) {
        log.info("Updating movie with ID: {}", id);

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));

        // Update genre if changed
        if (request.getGenreId() != null && !request.getGenreId().equals(movie.getGenre().getId())) {
            Genre genre = genreRepository.findById(request.getGenreId())
                    .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", request.getGenreId()));
            movie.setGenre(genre);
        }

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setRating(request.getRating());
        movie.setIsActive(request.getIsActive());

        Movie updatedMovie = movieRepository.save(movie);
        log.info("Movie updated successfully: {}", updatedMovie.getTitle());

        return mapToResponse(updatedMovie);
    }

    public void deleteMovie(Long id) {
        log.info("Deleting movie with ID: {}", id);

        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie", "id", id);
        }

        movieRepository.deleteById(id);
        log.info("Movie deleted successfully");
    }

    private MovieResponse mapToResponse(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getPosterUrl(),
                movie.getGenre().getId(),
                movie.getGenre().getName(),
                movie.getDurationMinutes(),
                movie.getReleaseDate(),
                movie.getRating(),
                movie.getIsActive()
        );
    }
}