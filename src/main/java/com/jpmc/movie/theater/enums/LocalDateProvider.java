package com.jpmc.movie.theater.enums;

import java.time.LocalDate;

/**
 * 1. In this implementation, INSTANCE is the single instance of the LocalDateProvider enum that is automatically created by the JVM
 * when the class is first accessed. The currentDate method is called on the singleton instance "INSTANCE".
 * 2. This approach is thread-safe, as enum values are guaranteed to be initialized only once, and it's simpler and cleaner than the previous implementation.
 * 3. The instances of enum values are created statically when the enum class is loaded, and the JVM ensures that only one instance of each enum value exists
 */
public enum LocalDateProvider {
    INSTANCE;

    public LocalDate currentDate() {
        return LocalDate.now();
    }
}