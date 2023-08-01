package com.booking.demo.application.exception;

import java.security.NoSuchAlgorithmException;

public class BookingNoSuchAlgorithmException extends RuntimeException {
    public BookingNoSuchAlgorithmException(NoSuchAlgorithmException e) {
        super(e);
    }
}
