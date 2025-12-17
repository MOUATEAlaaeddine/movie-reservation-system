package com.cinema.moviereservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private Long userId;
    private String username;
    private String email;
    private String role;
}