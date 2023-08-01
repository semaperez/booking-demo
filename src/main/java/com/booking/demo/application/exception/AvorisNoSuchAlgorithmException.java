package com.booking.demo.application.exception;

import java.security.NoSuchAlgorithmException;

public class AvorisNoSuchAlgorithmException extends RuntimeException {
    public AvorisNoSuchAlgorithmException(NoSuchAlgorithmException e) {
        super(e);
    }
}
