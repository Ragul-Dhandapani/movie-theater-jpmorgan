package com.jpmc.movie.theater.tests;

import com.jpmc.movie.theater.Theater;
import com.jpmc.movie.theater.dao.Customer;
import com.jpmc.movie.theater.enums.LocalDateProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Slf4j
public class TheaterTests {

    private Theater theater;

    @BeforeEach
    public void setup() {
        theater = new Theater(LocalDateProvider.INSTANCE);
    }

    @Test
    void totalFeeForCustomer() {
        var customer = Customer.builder().name("John Doe").id("unused-id").build();
        var reservation = theater.reserve(customer , 2 , 4);
        Assertions.assertEquals(50.0 , reservation.totalFee());
    }

    @Test
    void printMovieScheduleInTextFormat() {
        theater.printScheduleInTextFormat();
    }

    @Test
    void testHumanReadableFormat() {
        Duration duration = Duration.ofMinutes(135);
        String expectedOutput = "(2 hours 15 minutes)";
        Assertions.assertEquals(expectedOutput , theater.humanReadableFormat(duration));
    }

}
