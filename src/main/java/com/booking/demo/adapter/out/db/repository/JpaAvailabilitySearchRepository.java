package com.booking.demo.adapter.out.db.repository;

import com.booking.demo.adapter.out.db.model.AvailabilitySearchDocument;
import com.booking.demo.adapter.out.db.model.Search;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface JpaAvailabilitySearchRepository extends MongoRepository<AvailabilitySearchDocument, String> {
    List<AvailabilitySearchDocument> findBySearch(Search search);
    Optional<AvailabilitySearchDocument> findBySearchId(String searchId);
}
