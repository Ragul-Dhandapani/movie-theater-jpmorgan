package com.jpmc.movie.theater.tests.dao;

import com.jpmc.movie.theater.dao.Movie;
import com.jpmc.movie.theater.dao.Showing;
import com.jpmc.movie.theater.enums.LocalDateProvider;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTests {

    public static final String THE_BAT_MAN = "The BatMan";
    public static final String SPIDER_MAN_NO_WAY_HOME = "Spider-Man: No Way Home";
    public Movie movie;

    @Test
    void specialMovieWith50PercentDiscount() {
        movie = Movie.builder().title(SPIDER_MAN_NO_WAY_HOME).runningTime(Duration.ofMinutes(90))
                .ticketPrice(12.5).specialCode(1).build();
        var showing = Showing.builder().movie(movie).sequenceOfTheDay(5)
                .showStartTime(LocalDateTime.of(LocalDateProvider.INSTANCE.currentDate() , LocalTime.of(8 , 0))).build();
        assertEquals(10 , movie.calculateTicketPrice(showing));
    }

    @Test
    void nonSpecialMovie_OnSeventhSequenceDay_OneDollarDiscount() {
        movie = Movie.builder().title(SPIDER_MAN_NO_WAY_HOME).runningTime(Duration.ofMinutes(90))
                .ticketPrice(10).specialCode(0).build();
        var showing = Showing.builder().movie(movie).sequenceOfTheDay(7)
                .showStartTime(LocalDateTime.of(LocalDateProvider.INSTANCE.currentDate() , LocalTime.of(10 , 0))).build();
        assertEquals(9 , movie.calculateTicketPrice(showing));
    }

    @Test
    void OnSeventhSequenceDay_WithElevenAMMovie25PercentDiscount() {
        movie = Movie.builder().title(THE_BAT_MAN).runningTime(Duration.ofMinutes(90))
                .ticketPrice(10).specialCode(0).build();
        var showing = Showing.builder().movie(movie).sequenceOfTheDay(7)
                .showStartTime(LocalDateTime.of(LocalDateProvider.INSTANCE.currentDate() , LocalTime.of(11 , 0))).build();
        assertEquals(7.5 , movie.calculateTicketPrice(showing));
    }

    @Test
    void nonSpecialMovie_SequenceDay1_3DollarDiscount() {
        movie = Movie.builder().title(THE_BAT_MAN).runningTime(Duration.ofMinutes(90))
                .ticketPrice(10).specialCode(0).build();
        var showing = Showing.builder().movie(movie).sequenceOfTheDay(1)
                .showStartTime(LocalDateTime.of(LocalDateProvider.INSTANCE.currentDate() , LocalTime.of(11 , 0))).build();
        assertEquals(7 , movie.calculateTicketPrice(showing));
    }

    @Test
    void nonSpecialMovie_SequenceDay2_2DollarDiscount() {
        movie = Movie.builder().title(THE_BAT_MAN).runningTime(Duration.ofMinutes(90))
                .ticketPrice(10).specialCode(0).build();
        var showing = Showing.builder().movie(movie).sequenceOfTheDay(2)
                .showStartTime(LocalDateTime.of(LocalDateProvider.INSTANCE.currentDate() , LocalTime.of(5 , 0))).build();
        assertEquals(8 , movie.calculateTicketPrice(showing));
    }

    @Test
    void nonSpecialMovieWithNoDiscount() {
        movie = Movie.builder().title(THE_BAT_MAN).runningTime(Duration.ofMinutes(90))
                .ticketPrice(10).specialCode(0).build();
        var showing = Showing.builder().movie(movie).sequenceOfTheDay(6)
                .showStartTime(LocalDateTime.of(LocalDateProvider.INSTANCE.currentDate() , LocalTime.of(5 , 0))).build();
        assertEquals(10 , movie.calculateTicketPrice(showing));
    }
}
