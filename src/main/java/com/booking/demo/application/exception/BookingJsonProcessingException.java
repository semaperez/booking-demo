package com.booking.demo.application.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class BookingJsonProcessingException extends RuntimeException {
    public BookingJsonProcessingException(JsonProcessingException e) {
        super(e);
    }
}
