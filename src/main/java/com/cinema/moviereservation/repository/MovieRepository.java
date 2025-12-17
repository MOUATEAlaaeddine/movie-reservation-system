package com.cinema.moviereservation.repository;

import com.cinema.moviereservation.entity.Movie;
import com.cinema.moviereservation.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenre(Genre genre);

    List<Movie> findByIsActiveTrue();

    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByGenreId(Long genreId);
}