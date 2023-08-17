package com.booking.demo.adapter.out.db.repository;

import com.booking.demo.adapter.out.db.model.AvailabilitySearchDocument;
import com.booking.demo.adapter.out.db.model.SearchDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JpaAvailabilitySearchRepository extends MongoRepository<AvailabilitySearchDocument, String> {
    Optional<AvailabilitySearchDocument> findBySearchDocument(SearchDocument searchDocument);
    Optional<AvailabilitySearchDocument> findBySearchId(String searchId);
    void deleteBySearchId(String searchId);
}
