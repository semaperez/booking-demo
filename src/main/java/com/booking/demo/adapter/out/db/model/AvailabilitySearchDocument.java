package com.booking.demo.adapter.out.db.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hotel_availability_searches")
public class AvailabilitySearchDocument {
    @Id
    private String id;
    private String searchId;
    private SearchDocument searchDocument;
    private Integer count;

    public AvailabilitySearchDocument(String id, String searchId, SearchDocument searchDocument, Integer count) {
        this.id = id;
        this.searchId = searchId;
        this.searchDocument = searchDocument;
        this.count = count;
    }

    public AvailabilitySearchDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public SearchDocument getSearch() {
        return searchDocument;
    }

    public void setSearch(SearchDocument searchDocument) {
        this.searchDocument = searchDocument;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
