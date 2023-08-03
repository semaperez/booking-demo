package com.booking.demo.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Search {
    private String hotelId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<Integer> ages;

    public Search(String hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Search [");
        sb.append("hotelId=").append(hotelId);
        sb.append(", checkIn=").append(checkIn);
        sb.append(", checkOut=").append(checkOut);
        sb.append(", ages=").append(ages);
        sb.append("]");
        return sb.toString();
    }

}
