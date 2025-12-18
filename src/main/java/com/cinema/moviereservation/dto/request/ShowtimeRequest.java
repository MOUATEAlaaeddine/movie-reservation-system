package com.cinema.moviereservation.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeRequest {

    @NotNull(message = "Movie ID is required")
    private Long movieId;

    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "Room number is required")
    @Min(value = 1, message = "Room number must be at least 1")
    private Integer roomNumber;

    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Must have at least 1 seat")
    @Max(value = 500, message = "Cannot exceed 500 seats")
    private Integer totalSeats;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private Double price;
}