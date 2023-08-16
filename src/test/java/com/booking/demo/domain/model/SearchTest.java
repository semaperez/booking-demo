package com.booking.demo.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchTest {
    @Test
    void testToString() {
        LocalDate checkIn = LocalDate.of(2023,8,3);
        LocalDate checkOut = LocalDate.of(2023,8,4);
        Search search = new Search("hotelId", checkIn, checkOut, Collections.singletonList(1));
        final String expected = "Search [hotelId=hotelId, checkIn=2023-08-03, checkOut=2023-08-04, ages=[1]]";

        assertEquals(expected, search.toString());
    }
}

