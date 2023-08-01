package com.booking.demo.application.dto;

import java.time.LocalDateTime;

public class ErrorDto {
    private final String url;
    private final String errorMessage;
    private final LocalDateTime dateTime;

    public ErrorDto(String url, String errorMessage, LocalDateTime dateTime) {
        this.url = url;
        this.errorMessage = errorMessage;
        this.dateTime = dateTime;
    }

    public String getUrl() {
        return url;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
