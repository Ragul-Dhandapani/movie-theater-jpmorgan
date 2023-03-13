package com.jpmc.movie.theater.tests.dao;

import com.jpmc.movie.theater.dao.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerTests {

    @Test
    void customer_positiveScenario() {
        var customer = Customer.builder().name("Bob").id("Bob1234").build();
        Assertions.assertEquals(customer , Customer.builder().name("Bob").id("Bob1234").build());
    }

    @Test
    void customer_shouldThrowException_whenNullCustomerNameGiven() {
        Assertions.assertThrows(NullPointerException.class , ()-> Customer.builder().name(null).build());
    }

    @Test
    void customer_shouldThrowException_whenNullCustomerIdGiven() {
        Assertions.assertThrows(NullPointerException.class , ()-> Customer.builder().id(null).build());
    }
}
