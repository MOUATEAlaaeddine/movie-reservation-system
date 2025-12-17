package com.cinema.moviereservation.controller.auth;

import com.cinema.moviereservation.dto.*;
import com.cinema.moviereservation.dto.request.PromoteRequest;
import com.cinema.moviereservation.dto.request.RegisterRequest;
import com.cinema.moviereservation.dto.ApiResponse;
import com.cinema.moviereservation.dto.response.AuthResponse;
import com.cinema.moviereservation.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = userService.registerUser(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("User registered successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<AuthResponse>> getUserById(@PathVariable Long id) {
        try {
            AuthResponse response = userService.getUserById(id);
            return ResponseEntity.ok(ApiResponse.success("User found", response));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers(
            @RequestParam(required = false) String role) {
        try {
            List<UserDTO> users = userService.getAllUsers(role);
            return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/promote")
    public ResponseEntity<ApiResponse<AuthResponse>> promoteToAdmin(@RequestBody PromoteRequest request) {
        try {
            AuthResponse response = userService.promoteToAdmin(request.getUserId());
            return ResponseEntity.ok(ApiResponse.success("User promoted successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}