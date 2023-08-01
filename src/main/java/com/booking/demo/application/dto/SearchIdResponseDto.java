package com.booking.demo.application.dto;

public class SearchIdResponseDto {
    private String searchId;

    public SearchIdResponseDto(String searchId) {
        this.searchId = searchId;
    }

    public SearchIdResponseDto() {
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
}
