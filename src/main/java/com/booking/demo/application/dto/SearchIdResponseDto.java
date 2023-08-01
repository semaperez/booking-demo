package com.booking.demo.application.dto;

public class SearchIdResponseDto {
    private final String searchId;

    public SearchIdResponseDto(String searchId) {
        this.searchId = searchId;
    }

    public String getSearchId() {
        return searchId;
    }

}
