package com.cinema.moviereservation.service;

import com.cinema.moviereservation.dto.GenreDTO;
import com.cinema.moviereservation.entity.Genre;
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

    public GenreDTO createGenre(GenreDTO genreDTO) {
        log.info("Creating new genre: {}", genreDTO.getName());

        if (genreRepository.existsByName(genreDTO.getName())) {
            throw new RuntimeException("Genre already exists: " + genreDTO.getName());
        }

        Genre genre = new Genre();
        genre.setName(genreDTO.getName());
        genre.setDescription(genreDTO.getDescription());

        Genre savedGenre = genreRepository.save(genre);
        log.info("Genre created with ID: {}", savedGenre.getId());

        return convertToDTO(savedGenre);
    }

    public List<GenreDTO> getAllGenres() {
        log.info("Fetching all genres");
        return genreRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public GenreDTO getGenreById(Long id) {
        log.info("Fetching genre with ID: {}", id);
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found with ID: " + id));
        return convertToDTO(genre);
    }

    public GenreDTO updateGenre(Long id, GenreDTO genreDTO) {
        log.info("Updating genre with ID: {}", id);

        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        genre.setName(genreDTO.getName());
        genre.setDescription(genreDTO.getDescription());

        Genre updatedGenre = genreRepository.save(genre);
        return convertToDTO(updatedGenre);
    }

    public void deleteGenre(Long id) {
        log.info("Deleting genre with ID: {}", id);
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("Genre not found");
        }
        genreRepository.deleteById(id);
    }

    private GenreDTO convertToDTO(Genre genre) {
        return new GenreDTO(
                genre.getId(),
                genre.getName(),
                genre.getDescription()
        );
    }
}