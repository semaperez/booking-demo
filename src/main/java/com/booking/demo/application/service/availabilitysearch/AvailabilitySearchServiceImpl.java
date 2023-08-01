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
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AvailabilitySearchServiceImpl implements AvailabilitySearchUseCases {
    private JpaAvailabilitySearchRepository jpaAvailabilitySearchRepository;
    private AvailabilitySearchMapper availabilitySearchMapper;
    private SearchMapper searchMapper;
    private HashGenerator hashGenerator;

    public AvailabilitySearchServiceImpl(JpaAvailabilitySearchRepository jpaAvailabilitySearchRepository, AvailabilitySearchMapper availabilitySearchMapper, SearchMapper searchMapper
            , HashGenerator hashGenerator) {
        this.jpaAvailabilitySearchRepository = jpaAvailabilitySearchRepository;
        this.availabilitySearchMapper = availabilitySearchMapper;
        this.searchMapper = searchMapper;
        this.hashGenerator = hashGenerator;
    }

    @Override
    public Optional<AvailabilitySearchDto> getBySearchId(String searchId) {
        return jpaAvailabilitySearchRepository.findBySearchId(searchId).map(availabilitySearchMapper::toDto);
    }

    @Override
    public SearchIdResponseDto registerSearch(SearchDto searchDto) {
        Search search = searchMapper.toDocument(searchDto);

        String hash = hashGenerator.generateHashFromObject(search);

        AvailabilitySearchDocument availabilitySearchDocument =
                jpaAvailabilitySearchRepository.findBySearch(search).stream().findFirst().orElse(new AvailabilitySearchDocument());
        availabilitySearchDocument.setSearchId(hash);
        availabilitySearchDocument.setSearch(search);
        availabilitySearchDocument.setCount(Objects.requireNonNullElse(availabilitySearchDocument.getCount(),0)+1);
        jpaAvailabilitySearchRepository.save(availabilitySearchDocument);

        return new SearchIdResponseDto(hash);
    }

}
