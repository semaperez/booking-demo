package com.avoristech.demo.application.dto;

public class AccessLogCountDto {
    private String searchId;
    private SearchDto search;
    private Integer count;
    public AccessLogCountDto(String searchId, SearchDto search, Integer count) {
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
