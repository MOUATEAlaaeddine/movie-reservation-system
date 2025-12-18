package com.cinema.moviereservation.controller.movie;

import com.cinema.moviereservation.dto.ApiResponse;
import com.cinema.moviereservation.dto.request.ShowtimeRequest;
import com.cinema.moviereservation.dto.response.ShowtimeResponse;
import com.cinema.moviereservation.service.movie.ShowtimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    @PostMapping
    public ResponseEntity<ApiResponse<ShowtimeResponse>> createShowtime(
            @Valid @RequestBody ShowtimeRequest request) {
        ShowtimeResponse response = showtimeService.createShowtime(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Showtime created", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getAllShowtimes() {
        List<ShowtimeResponse> showtimes = showtimeService.getAllShowtimes();
        return ResponseEntity.ok(ApiResponse.success("Showtimes retrieved", showtimes));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getUpcomingShowtimes() {
        List<ShowtimeResponse> showtimes = showtimeService.getUpcomingShowtimes();
        return ResponseEntity.ok(ApiResponse.success("Upcoming showtimes", showtimes));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getShowtimesByMovie(
            @PathVariable Long movieId) {
        List<ShowtimeResponse> showtimes = showtimeService.getShowtimesByMovie(movieId);
        return ResponseEntity.ok(ApiResponse.success("Movie showtimes", showtimes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowtimeResponse>> getShowtimeById(@PathVariable Long id) {
        ShowtimeResponse showtime = showtimeService.getShowtimeById(id);
        return ResponseEntity.ok(ApiResponse.success("Showtime found", showtime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.ok(ApiResponse.success("Showtime deleted", null));
    }
}