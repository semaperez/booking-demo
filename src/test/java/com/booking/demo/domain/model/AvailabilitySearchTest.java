package com.booking.demo.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvailabilitySearchTest {
    @Test
    void testIncrementCount() {
        AvailabilitySearch availabilitySearch = buildAvailabilitySearch();
        availabilitySearch.incrementCount();
        assertEquals(2, availabilitySearch.getCount());
    }

    private AvailabilitySearch buildAvailabilitySearch(){
        return new AvailabilitySearch("SearchId", buildSearch(), 1);
    }
    private Search buildSearch(){
        return new Search("HotelId", LocalDate.now(), LocalDate.now(), Collections.singletonList(1));
    }

}

