package com.booking.demo.application.service.availabilitysearch;

import com.booking.demo.adapter.out.db.model.AvailabilitySearchDocument;
import com.booking.demo.adapter.out.db.model.SearchDocument;
import com.booking.demo.adapter.out.db.repository.JpaAvailabilitySearchRepository;
import com.booking.demo.application.dto.AvailabilitySearchDto;
import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.dto.SearchIdResponseDto;
import com.booking.demo.application.service.availabilitysearch.mapper.AvailabilitySearchMapper;
import com.booking.demo.application.service.availabilitysearch.mapper.SearchMapper;
import com.booking.demo.domain.model.AvailabilitySearch;
import com.booking.demo.domain.model.Search;
import com.booking.demo.domain.service.AvailabilitySearchDomainService;
import com.booking.demo.port.out.messaging.AvailabilitySearchProducerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AvailabilitySearchApplicationServiceImpl implements AvailabilitySearchUseCases {
    private final JpaAvailabilitySearchRepository jpaAvailabilitySearchRepository;
    private final AvailabilitySearchMapper availabilitySearchMapper;
    private final SearchMapper searchMapper;
    private final AvailabilitySearchProducerPort availabilitySearchProducerPort;
    private final AvailabilitySearchDomainService availabilitySearchDomainService;

    public AvailabilitySearchApplicationServiceImpl(JpaAvailabilitySearchRepository jpaAvailabilitySearchRepository,
                                                    AvailabilitySearchMapper availabilitySearchMapper, SearchMapper searchMapper,
                                                    AvailabilitySearchProducerPort availabilitySearchProducerPort,
                                                    AvailabilitySearchDomainService availabilitySearchDomainService) {
        this.jpaAvailabilitySearchRepository = jpaAvailabilitySearchRepository;
        this.availabilitySearchMapper = availabilitySearchMapper;
        this.searchMapper = searchMapper;
        this.availabilitySearchProducerPort = availabilitySearchProducerPort;
        this.availabilitySearchDomainService = availabilitySearchDomainService;
    }

    @Override
    public Optional<AvailabilitySearchDto> getBySearchId(String searchId) {
        return jpaAvailabilitySearchRepository.findBySearchId(searchId).map(availabilitySearchMapper::toDto);
    }

    @Override
    public SearchIdResponseDto registerSearch(SearchDto searchDto) {
        Search search = searchMapper.toDomain(searchDto);
        AvailabilitySearch availabilitySearch = availabilitySearchDomainService.build(search);

        availabilitySearchProducerPort.send(searchDto);

        return new SearchIdResponseDto(availabilitySearch.getSearchId());
    }

    @Override
    @Transactional
    public void persistSearch(SearchDto searchDto) {
        SearchDocument searchDocument = searchMapper.toDocument(searchDto);
        Search search = searchMapper.toDomain(searchDto);

        Optional<AvailabilitySearchDocument> availabilitySearchDocumentOptional =
                jpaAvailabilitySearchRepository.findBySearchDocument(searchDocument);

        AvailabilitySearch availabilitySearch;
        AvailabilitySearchDocument availabilitySearchDocument;
        if (availabilitySearchDocumentOptional.isPresent()){
            availabilitySearchDocument = availabilitySearchDocumentOptional.get();
            availabilitySearch = availabilitySearchMapper.toDomain(availabilitySearchDocument);
        } else {
            availabilitySearchDocument = new AvailabilitySearchDocument();
            availabilitySearch = availabilitySearchDomainService.build(search);
        }

        availabilitySearch.incrementCount();

        availabilitySearchDocument = availabilitySearchMapper.toDocument(availabilitySearch, availabilitySearchDocument.getId());

        jpaAvailabilitySearchRepository.save(availabilitySearchDocument);
    }
}
