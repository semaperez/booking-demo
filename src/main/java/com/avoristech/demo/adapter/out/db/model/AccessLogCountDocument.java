package com.avoristech.demo.adapter.out.db.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accesslogcount")
public class AccessLogCountDocument {
    @Id
    private String id;
    private String searchId;
    private Search search;
    private Integer count;

    public AccessLogCountDocument(String id, String searchId, Search search, Integer count) {
        this.id = id;
        this.searchId = searchId;
        this.search = search;
        this.count = count;
    }

    public AccessLogCountDocument() {
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

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
