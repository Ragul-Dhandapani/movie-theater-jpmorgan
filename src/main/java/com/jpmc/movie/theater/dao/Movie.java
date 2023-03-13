package com.jpmc.movie.theater.dao;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.jpmc.movie.theater.util.MovieUtil.getSequenceDiscount;

@Data
@EqualsAndHashCode
@ToString
@Builder
public class Movie {
    private static final int MOVIE_CODE_SPECIAL = 1;
    private static String description; // as we are not using this field anywhere changed too static to ignore from lombok
    private String title;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public double calculateTicketPrice(Showing showing) {
        double discount = getDiscount(showing.getSequenceOfTheDay() , showing.getShowStartTime());
        return getTicketPrice() - Math.max(0 , discount);
    }

    private double getDiscount(int showSequence , LocalDateTime showStartTime) {
        double specialDiscount = 0;
        if (MOVIE_CODE_SPECIAL == getSpecialCode()) {
            specialDiscount = getTicketPrice() * 0.2;  // 20% discount for special movie
        }

        double sequenceDiscount = getSequenceDiscount(showSequence);

        double timeDiscount = 0;
        LocalTime startTime = showStartTime.toLocalTime();
        if ((startTime.isAfter(LocalTime.of(11 , 0)) || startTime.equals(LocalTime.of(11 , 0))) &&
                startTime.isBefore(LocalTime.of(16 , 0))) {
            timeDiscount = getTicketPrice() * 0.25; // 25% discount for shows starting between 11AM ~ 4PM
        }

        // biggest discount wins
        return Math.max(Math.max(specialDiscount , sequenceDiscount) , timeDiscount);
    }


}