package com.booking.demo.domain.model;

import java.util.Objects;

public class AvailabilitySearch {
    private String searchId;
    private Search search;
    private Integer count;

    public AvailabilitySearch(String searchId, Search search, Integer count) {
        this.searchId = searchId;
        this.search = search;
        this.count = count;
    }

    public String getSearchId() {
        return searchId;
    }

    public Search getSearch() {
        return search;
    }

    public Integer getCount() {
        return count;
    }

    public void incrementCount() {
        this.count=(Objects.requireNonNullElse(count,0)+1);
    }

}
