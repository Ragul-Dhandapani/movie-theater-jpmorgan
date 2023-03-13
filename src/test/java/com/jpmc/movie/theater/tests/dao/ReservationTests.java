package com.jpmc.movie.theater.tests.dao;

import com.jpmc.movie.theater.Theater;
import com.jpmc.movie.theater.dao.Customer;
import com.jpmc.movie.theater.dao.Movie;
import com.jpmc.movie.theater.dao.Reservation;
import com.jpmc.movie.theater.dao.Showing;
import com.jpmc.movie.theater.enums.LocalDateProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

class ReservationTests {

    private Theater theater;

    @BeforeEach
    public void setup() {
        theater = new Theater(LocalDateProvider.INSTANCE);
    }

    @Test
    void totalFee() {
        var customer = Customer.builder().name("John Doe").id("unused-id").build();
        Movie movie = Movie.builder().title("Spider-Man: No Way Home").runningTime(Duration.ofMinutes(90))
                .ticketPrice(12.5).build();
        var showing = Showing.builder().movie(movie).sequenceOfTheDay(1)
                .showStartTime(LocalDateTime.now()).build();
        var totalFee = Reservation.builder().customer(customer).showing(showing).audienceCount(3).build().totalFee();
        Assertions.assertEquals(37.5 , totalFee);
    }


    @Test
    void reserve_shouldReserveTickets_whenEnoughTicketsAvailable() {
        var customer = Customer.builder().name("John Doe").build();
        Reservation reservation = theater.reserve(customer , 2 , 2);
        Assertions.assertNotNull(reservation);
        Assertions.assertEquals(customer , reservation.getCustomer());
        Assertions.assertEquals(2 , reservation.getAudienceCount());
    }

    @Test
    void reserve_shouldThrowException_whenInvalidSequenceNumberGiven() {
        var customer = Customer.builder().name("Bob").build();
        Assertions.assertThrows(IllegalStateException.class , () -> theater.reserve(customer , 10 , 1));
    }

    @Test
    void reserve_shouldThrowException_whenNullCustomerGiven() {
        Assertions.assertThrows(NullPointerException.class , () -> theater.reserve(null , 1 , 1));
    }

    @Test
    void reserve_shouldThrowException_whenInvalidNumberOfTicketsGiven() {
        var customer = Customer.builder().name("Alice").build();
        Assertions.assertThrows(IllegalArgumentException.class , () -> theater.reserve(customer , 3 , -1));
    }

}
