package com.booking.demo.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SearchIdResponseDto {
    private final String searchId;
    @JsonCreator
    public SearchIdResponseDto(String searchId) {
        this.searchId = searchId;
    }

    public String getSearchId() {
        return searchId;
    }

}
