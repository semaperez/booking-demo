package com.booking.demo.application.service.common;

import com.booking.demo.application.exception.BookingNoSuchAlgorithmException;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashGenerator {
    private final JsonConverter jsonConverter;
    public HashGenerator(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }
    public String generateHashFromObject(Object object) {
        return generateHashFromJson(jsonConverter.convertToJson(object));
    }

    private String generateHashFromJson(String json){
        byte[] jsonData = json.getBytes();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new BookingNoSuchAlgorithmException(e);
        }

        byte[] hashBytes = md.digest(jsonData);

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
