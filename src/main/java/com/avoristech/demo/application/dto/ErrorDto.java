package com.avoristech.demo.application.dto;

import java.time.LocalDateTime;

public class ErrorDto {
    String url;
    String errorMessage;
    LocalDateTime dateTime;

    public ErrorDto(String url, String errorMessage, LocalDateTime dateTime) {
        this.url = url;
        this.errorMessage = errorMessage;
        this.dateTime = dateTime;
    }
}
