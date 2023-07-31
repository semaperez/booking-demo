package com.avoristech.demo.adapter.out.db;

import com.avoristech.demo.adapter.out.db.documents.AccessLogCountDocument;
import com.avoristech.demo.adapter.out.db.documents.Search;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface JpaAccessLogCountRepository extends MongoRepository<AccessLogCountDocument, String> {
}
