package com.cinema.moviereservation.repository;

import com.cinema.moviereservation.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    List<Showtime> findByMovieId(Long movieId);

    List<Showtime> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT s FROM Showtime s WHERE s.startTime > :now ORDER BY s.startTime")
    List<Showtime> findUpcomingShowtimes(LocalDateTime now);

    List<Showtime> findByRoomNumberAndStartTimeBetween(Integer roomNumber, LocalDateTime start, LocalDateTime end);
}