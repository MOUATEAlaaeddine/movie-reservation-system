package com.cinema.moviereservation.controller;

import com.cinema.moviereservation.dto.AuthResponse;
import com.cinema.moviereservation.dto.RegisterRequest;
import com.cinema.moviereservation.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.cinema.moviereservation.dto.UserDTO;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false) String role) {
        try {
            List<UserDTO> users = userService.getAllUsers(role);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}