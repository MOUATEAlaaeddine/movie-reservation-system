package com.cinema.moviereservation.util;

public class Constants {

    // API Paths
    public static final String API_BASE = "/api";
    public static final String API_AUTH = API_BASE + "/auth";
    public static final String API_MOVIES = API_BASE + "/movies";
    public static final String API_GENRES = API_BASE + "/genres";
    public static final String API_RESERVATIONS = API_BASE + "/reservations";
    public static final String API_ADMIN = API_BASE + "/admin";

    // Roles
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    // Validation Messages
    public static final String VALIDATION_EMAIL_INVALID = "Email should be valid";
    public static final String VALIDATION_PASSWORD_MIN = "Password must be at least 6 characters";
    public static final String VALIDATION_USERNAME_REQUIRED = "Username is required";

    // Error Messages
    public static final String ERROR_USER_NOT_FOUND = "User not found";
    public static final String ERROR_MOVIE_NOT_FOUND = "Movie not found";
    public static final String ERROR_GENRE_NOT_FOUND = "Genre not found";
    public static final String ERROR_USERNAME_EXISTS = "Username already exists";
    public static final String ERROR_EMAIL_EXISTS = "Email already exists";

    // Success Messages
    public static final String SUCCESS_USER_REGISTERED = "User registered successfully";
    public static final String SUCCESS_MOVIE_CREATED = "Movie created successfully";
    public static final String SUCCESS_GENRE_CREATED = "Genre created successfully";

    private Constants() {
        // Private constructor to prevent instantiation
    }
}