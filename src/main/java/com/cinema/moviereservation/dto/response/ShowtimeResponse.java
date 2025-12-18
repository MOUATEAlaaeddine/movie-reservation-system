package com.cinema.moviereservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeResponse {
    private Long id;
    private Long movieId;
    private String movieTitle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer roomNumber;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double price;
}