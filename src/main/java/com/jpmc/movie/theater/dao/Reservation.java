package com.jpmc.movie.theater.dao;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Reservation {
    private @NonNull Customer customer;
    private @NonNull Showing showing;
    private int audienceCount;

    public double totalFee() {
        return showing.getMovieFee() * getAudienceCount();
    }
}