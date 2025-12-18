package com.cinema.moviereservation.service.movie;

import com.cinema.moviereservation.dto.request.ShowtimeRequest;
import com.cinema.moviereservation.dto.response.ShowtimeResponse;
import com.cinema.moviereservation.entity.Movie;
import com.cinema.moviereservation.entity.Showtime;
import com.cinema.moviereservation.exception.BadRequestException;
import com.cinema.moviereservation.exception.ResourceNotFoundException;
import com.cinema.moviereservation.repository.MovieRepository;
import com.cinema.moviereservation.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    public ShowtimeResponse createShowtime(ShowtimeRequest request) {
        log.info("Creating showtime for movie ID: {}", request.getMovieId());

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", request.getMovieId()));

        // Calculate end time
        LocalDateTime endTime = request.getStartTime().plusMinutes(movie.getDurationMinutes());

        // Check room availability
        List<Showtime> conflictingShowtimes = showtimeRepository
                .findByRoomNumberAndStartTimeBetween(
                        request.getRoomNumber(),
                        request.getStartTime().minusMinutes(30),
                        endTime.plusMinutes(30)
                );

        if (!conflictingShowtimes.isEmpty()) {
            throw new BadRequestException("Room " + request.getRoomNumber() + " is not available at this time");
        }

        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setStartTime(request.getStartTime());
        showtime.setEndTime(endTime);
        showtime.setRoomNumber(request.getRoomNumber());
        showtime.setTotalSeats(request.getTotalSeats());
        showtime.setAvailableSeats(request.getTotalSeats());
        showtime.setPrice(request.getPrice());

        Showtime saved = showtimeRepository.save(showtime);
        log.info("Showtime created with ID: {}", saved.getId());

        return mapToResponse(saved);
    }

    public List<ShowtimeResponse> getAllShowtimes() {
        return showtimeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ShowtimeResponse> getUpcomingShowtimes() {
        return showtimeRepository.findUpcomingShowtimes(LocalDateTime.now()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ShowtimeResponse> getShowtimesByMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Movie", "id", movieId);
        }
        return showtimeRepository.findByMovieId(movieId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ShowtimeResponse getShowtimeById(Long id) {
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime", "id", id));
        return mapToResponse(showtime);
    }

    public void deleteShowtime(Long id) {
        if (!showtimeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Showtime", "id", id);
        }
        showtimeRepository.deleteById(id);
        log.info("Showtime deleted: {}", id);
    }

    private ShowtimeResponse mapToResponse(Showtime showtime) {
        return new ShowtimeResponse(
                showtime.getId(),
                showtime.getMovie().getId(),
                showtime.getMovie().getTitle(),
                showtime.getStartTime(),
                showtime.getEndTime(),
                showtime.getRoomNumber(),
                showtime.getTotalSeats(),
                showtime.getAvailableSeats(),
                showtime.getPrice()
        );
    }
}