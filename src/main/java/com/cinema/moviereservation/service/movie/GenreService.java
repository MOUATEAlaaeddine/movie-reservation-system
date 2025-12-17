package com.cinema.moviereservation.service.movie;

import com.cinema.moviereservation.dto.request.GenreRequest;
import com.cinema.moviereservation.dto.response.GenreResponse;
import com.cinema.moviereservation.entity.Genre;
import com.cinema.moviereservation.exception.DuplicateResourceException;
import com.cinema.moviereservation.exception.ResourceNotFoundException;
import com.cinema.moviereservation.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreResponse createGenre(GenreRequest request) {
        log.info("Creating new genre: {}", request.getName());

        if (genreRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Genre", "name", request.getName());
        }

        Genre genre = new Genre();
        genre.setName(request.getName());
        genre.setDescription(request.getDescription());

        Genre savedGenre = genreRepository.save(genre);
        log.info("Genre created successfully with ID: {}", savedGenre.getId());

        return mapToResponse(savedGenre);
    }

    public List<GenreResponse> getAllGenres() {
        log.info("Fetching all genres");
        return genreRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public GenreResponse getGenreById(Long id) {
        log.info("Fetching genre with ID: {}", id);
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", id));
        return mapToResponse(genre);
    }

    public GenreResponse updateGenre(Long id, GenreRequest request) {
        log.info("Updating genre with ID: {}", id);

        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", id));

        // Check if new name already exists (but not on this genre)
        if (!genre.getName().equals(request.getName()) &&
                genreRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Genre", "name", request.getName());
        }

        genre.setName(request.getName());
        genre.setDescription(request.getDescription());

        Genre updatedGenre = genreRepository.save(genre);
        log.info("Genre updated successfully: {}", updatedGenre.getName());

        return mapToResponse(updatedGenre);
    }

    public void deleteGenre(Long id) {
        log.info("Deleting genre with ID: {}", id);

        if (!genreRepository.existsById(id)) {
            throw new ResourceNotFoundException("Genre", "id", id);
        }

        genreRepository.deleteById(id);
        log.info("Genre deleted successfully");
    }

    private GenreResponse mapToResponse(Genre genre) {
        return new GenreResponse(
                genre.getId(),
                genre.getName(),
                genre.getDescription()
        );
    }
}