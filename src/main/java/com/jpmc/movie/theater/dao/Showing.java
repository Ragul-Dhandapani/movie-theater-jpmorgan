package com.jpmc.movie.theater.dao;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
public class Showing {
    private @NonNull Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    public double getMovieFee() {
        return movie.getTicketPrice();
    }

}
