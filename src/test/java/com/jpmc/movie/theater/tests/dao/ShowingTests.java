package com.jpmc.movie.theater.tests.dao;

import com.jpmc.movie.theater.dao.Movie;
import com.jpmc.movie.theater.dao.Showing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

class ShowingTests {

    @Test
    public void testGetMovieFee_ReturnsCorrectMovieFee() {
        var showing = Showing.builder()
                .movie(Movie.builder().title("The Matrix").runningTime(Duration.ofMinutes(150))
                        .ticketPrice(12.99).build() )
                .sequenceOfTheDay(1)
                .showStartTime(LocalDateTime.now())
                .build();
        Assertions.assertEquals(12.99, showing.getMovieFee(), 0.001);
    }

    @Test
    public void testGetMovieFee_ReturnsZeroMovieFee() {
        var showing = Showing.builder()
                .movie(Movie.builder().title("The Matrix").runningTime(Duration.ofMinutes(150)).ticketPrice(0).build())
                .sequenceOfTheDay(1)
                .showStartTime(LocalDateTime.now())
                .build();
        Assertions.assertEquals(0, showing.getMovieFee(), 0.001);
    }

}
