package com.jpmc.movie.theater.tests.enums;

import com.jpmc.movie.theater.enums.LocalDateProvider;
import org.junit.jupiter.api.Test;

public class LocalDateProviderTests {
    @Test
    void makeSureCurrentTime() {
        System.out.println("current time is - " + LocalDateProvider.INSTANCE);
    }
}
