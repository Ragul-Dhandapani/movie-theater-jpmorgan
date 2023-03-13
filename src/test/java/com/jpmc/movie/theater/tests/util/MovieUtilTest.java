package com.jpmc.movie.theater.tests.util;

import com.jpmc.movie.theater.util.MovieUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MovieUtilTest {

    @Test
    void getSequenceDiscountReturnsCorrectDiscount() {
        Assertions.assertEquals(3, MovieUtil.getSequenceDiscount(1));
        Assertions.assertEquals(2, MovieUtil.getSequenceDiscount(2));
        Assertions.assertEquals(1, MovieUtil.getSequenceDiscount(7));
        Assertions.assertEquals(0, MovieUtil.getSequenceDiscount(3));
    }
}