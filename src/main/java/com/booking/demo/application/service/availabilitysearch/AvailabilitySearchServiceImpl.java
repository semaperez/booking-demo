package com.booking.demo.application.service.availabilitysearch;

import com.booking.demo.adapter.out.db.model.AvailabilitySearchDocument;
import com.booking.demo.adapter.out.db.model.Search;
import com.booking.demo.adapter.out.db.repository.JpaAvailabilitySearchRepository;
import com.booking.demo.application.dto.AvailabilitySearchDto;
import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.dto.SearchIdResponseDto;
import com.booking.demo.application.service.availabilitysearch.mapper.AvailabilitySearchMapper;
import com.booking.demo.application.service.availabilitysearch.mapper.SearchMapper;
import com.booking.demo.application.service.common.HashGenerator;
import com.booking.demo.port.out.messaging.AvailabilitySearchProducerPort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AvailabilitySearchServiceImpl implements AvailabilitySearchUseCases {
    private final JpaAvailabilitySearchRepository jpaAvailabilitySearchRepository;
    private final AvailabilitySearchMapper availabilitySearchMapper;
    private final SearchMapper searchMapper;
    private final HashGenerator hashGenerator;
    private final AvailabilitySearchProducerPort availabilitySearchProducerPort;

    public AvailabilitySearchServiceImpl(JpaAvailabilitySearchRepository jpaAvailabilitySearchRepository,
                                         AvailabilitySearchMapper availabilitySearchMapper, SearchMapper searchMapper,
                                         AvailabilitySearchProducerPort availabilitySearchProducerPort
            , HashGenerator hashGenerator) {
        this.jpaAvailabilitySearchRepository = jpaAvailabilitySearchRepository;
        this.availabilitySearchMapper = availabilitySearchMapper;
        this.searchMapper = searchMapper;
        this.hashGenerator = hashGenerator;
        this.availabilitySearchProducerPort = availabilitySearchProducerPort;
    }

    @Override
    public Optional<AvailabilitySearchDto> getBySearchId(String searchId) {
        return jpaAvailabilitySearchRepository.findBySearchId(searchId).map(availabilitySearchMapper::toDto);
    }

    @Override
    public SearchIdResponseDto registerSearch(SearchDto searchDto) {
        Search search = searchMapper.toDocument(searchDto);
        availabilitySearchProducerPort.publish(searchDto);
        String hash = hashGenerator.generateHashFromObject(search);
        return new SearchIdResponseDto(hash);
    }

    @Override
    public void persistSearch(SearchDto searchDto) {
        Search search = searchMapper.toDocument(searchDto);
        String hash = hashGenerator.generateHashFromObject(search);

        AvailabilitySearchDocument availabilitySearchDocument =
                jpaAvailabilitySearchRepository.findBySearch(search).stream().findFirst().orElse(new AvailabilitySearchDocument());
        availabilitySearchDocument.setSearchId(hash);
        availabilitySearchDocument.setSearch(search);
        availabilitySearchDocument.setCount(Objects.requireNonNullElse(availabilitySearchDocument.getCount(),0)+1);
        jpaAvailabilitySearchRepository.save(availabilitySearchDocument);
    }
}
