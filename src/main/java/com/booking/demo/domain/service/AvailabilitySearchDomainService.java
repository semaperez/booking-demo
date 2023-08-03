package com.booking.demo.domain.service;

import com.booking.demo.domain.model.AvailabilitySearch;
import com.booking.demo.domain.model.Search;

public interface AvailabilitySearchDomainService {
    AvailabilitySearch build(Search search);
}
