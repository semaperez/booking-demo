package com.booking.demo.domain.service;

import com.booking.demo.application.exception.BookingNoSuchAlgorithmException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Component
public class HashGenerator {
    public String generateHashFromObject(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String objectString = object.toString();
            byte[] hashBytes = md.digest(objectString.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BookingNoSuchAlgorithmException(e);
        }
    }
}
