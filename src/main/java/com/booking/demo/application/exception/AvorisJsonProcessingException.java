package com.booking.demo.application.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class AvorisJsonProcessingException extends RuntimeException {
    public AvorisJsonProcessingException(JsonProcessingException e) {
        super(e);
    }
}
