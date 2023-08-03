package com.booking.demo.domain.service;

import com.booking.demo.domain.model.AvailabilitySearch;
import com.booking.demo.domain.model.Search;
import org.springframework.stereotype.Service;

@Service
public class AvailabilitySearchDomainServiceImpl implements AvailabilitySearchDomainService {
    private final HashGenerator hashGenerator;
    public AvailabilitySearchDomainServiceImpl(HashGenerator hashGenerator) {
        this.hashGenerator = hashGenerator;
    }
    @Override
    public AvailabilitySearch build(Search search) {
        String searchId = hashGenerator.generateHashFromObject(search);
        return new AvailabilitySearch(searchId, search, 0);
    }
}
