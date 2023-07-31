package com.avoristech.demo.adapter.out.db.repository;

import com.avoristech.demo.adapter.out.db.model.AccessLogCountDocument;
import com.avoristech.demo.adapter.out.db.model.Search;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface JpaAccessLogCountRepository extends MongoRepository<AccessLogCountDocument, String> {
    List<AccessLogCountDocument> findBySearch(Search search);
    Optional<AccessLogCountDocument> findBySearchId(String searchId);
}
