package com.jpmc.movie.theater.util;

import java.util.Map;

public class MovieUtil {

    private MovieUtil(){}

    /**
     * This implementation is more concise and easier to maintain than the original if-else statement.
     * If we need to add or modify discount values, we can simply update the map instead of changing the code
     */
    private static final Map<Integer, Integer> DISCOUNTS_BY_SEQUENCE = Map.of(
            1, 3, // $3 discount for 1st show
            2, 2, // $2 discount for 2nd show
            7, 1 // $1 discount for shows on the 7th of the month
    );

    public static int getSequenceDiscount(int showSequence) {
        return DISCOUNTS_BY_SEQUENCE.getOrDefault(showSequence, 0);
    }
}
