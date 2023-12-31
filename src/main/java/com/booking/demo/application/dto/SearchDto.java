package com.booking.demo.application.dto;

import java.time.LocalDate;
import java.util.List;

public class SearchDto {
    private final String hotelId;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private List<Integer> ages;
    public SearchDto(String hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages) {
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }

    public String getHotelId() {
        return hotelId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public List<Integer> getAges() {
        return ages;
    }
}
