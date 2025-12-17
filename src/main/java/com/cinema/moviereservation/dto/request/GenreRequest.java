package com.cinema.moviereservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreRequest {

    @NotBlank(message = "Genre name is required")
    @Size(min = 2, max = 50, message = "Genre name must be between 2 and 50 characters")
    private String name;

    @Size(max = 500, message = "Description too long")
    private String description;
}