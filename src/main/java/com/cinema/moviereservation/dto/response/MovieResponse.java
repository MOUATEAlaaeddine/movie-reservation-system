package com.cinema.moviereservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private String description;
    private String posterUrl;
    private Long genreId;
    private String genreName;
    private Integer durationMinutes;
    private LocalDate releaseDate;
    private Double rating;
    private Boolean isActive;
}