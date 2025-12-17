package com.cinema.moviereservation.service;

import com.cinema.moviereservation.dto.response.AuthResponse;
import com.cinema.moviereservation.dto.request.RegisterRequest;
import com.cinema.moviereservation.dto.UserDTO;
import com.cinema.moviereservation.entity.User;
import com.cinema.moviereservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponse registerUser(RegisterRequest request) {
        log.info("Attempting to register user: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Registration failed: Username {} already exists", request.getUsername());
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration failed: Email {} already exists", request.getEmail());
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.USER);

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {} with ID: {}",
                savedUser.getUsername(), savedUser.getId());

        return new AuthResponse(
                "User registered successfully",
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().toString()
        );
    }

    public AuthResponse getUserById(Long userId) {
        log.info("Fetching user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponse(
                "User found",
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().toString()
        );
    }

    public List<UserDTO> getAllUsers(String role) {
        log.info("Fetching all users with role filter: {}", role);
        List<User> users;

        if (role != null && !role.isEmpty()) {
            User.Role roleEnum = User.Role.valueOf(role.toUpperCase());
            users = userRepository.findByRole(roleEnum);
        } else {
            users = userRepository.findAll();
        }

        return users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole().toString()
                ))
                .collect(Collectors.toList());
    }

    public AuthResponse promoteToAdmin(Long userId) {
        log.info("Attempting to promote user {} to admin", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == User.Role.ADMIN) {
            throw new RuntimeException("User is already an admin");
        }

        user.setRole(User.Role.ADMIN);
        User updatedUser = userRepository.save(user);

        log.info("User {} promoted to admin successfully", userId);

        return new AuthResponse(
                "User promoted to admin successfully",
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getRole().toString()
        );
    }
}