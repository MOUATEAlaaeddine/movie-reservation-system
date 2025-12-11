package com.cinema.moviereservation.service;

import com.cinema.moviereservation.dto.AuthResponse;
import com.cinema.moviereservation.dto.RegisterRequest;
import com.cinema.moviereservation.dto.UserDTO;
import com.cinema.moviereservation.entity.User;
import com.cinema.moviereservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public AuthResponse registerUser(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.USER);

        // Save to database
        User savedUser = userRepository.save(user);

        // Return response
        return new AuthResponse(
                "User registered successfully",
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().toString()
        );
    }
    public List<UserDTO> getAllUsers(String role) {
        List<User> users;

        if (role != null && !role.isEmpty()) {
            // Filter by role
            User.Role roleEnum = User.Role.valueOf(role.toUpperCase());
            users = userRepository.findByRole(roleEnum);
        } else {
            // Get all users
            users = userRepository.findAll();
        }

        // Convert entities to DTOs
        return users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole().toString()
                ))
                .collect(Collectors.toList());
    }
}