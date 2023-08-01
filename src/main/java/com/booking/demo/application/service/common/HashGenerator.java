package com.booking.demo.application.service.common;

import com.booking.demo.application.exception.AvorisJsonProcessingException;
import com.booking.demo.application.exception.AvorisNoSuchAlgorithmException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashGenerator {
    private ObjectMapper objectMapper;

    public HashGenerator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String generateHashFromObject(Object object) {

        try {
            return generateHashFromJson(objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new AvorisJsonProcessingException(e);
        }
    }

    private String generateHashFromJson(String json){
        byte[] jsonData = json.getBytes();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new AvorisNoSuchAlgorithmException(e);
        }

        byte[] hashBytes = md.digest(jsonData);

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
