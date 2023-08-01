package com.booking.demo.application.dto;

public class AvailabilitySearchDto {
    private String searchId;
    private SearchDto search;
    private Integer count;
    public AvailabilitySearchDto(String searchId, SearchDto search, Integer count) {
        this.searchId = searchId;
        this.search = search;
        this.count = count;
    }

    public String getSearchId() {
        return searchId;
    }

    public SearchDto getSearch() {
        return search;
    }

    public Integer getCount() {
        return count;
    }

}
