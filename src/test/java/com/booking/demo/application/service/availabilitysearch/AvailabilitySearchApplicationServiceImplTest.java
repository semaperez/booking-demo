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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailabilitySearchApplicationServiceImplTest {
    @Mock
    private JpaAvailabilitySearchRepository jpaAvailabilitySearchRepository;

    @Mock
    private AvailabilitySearchMapper availabilitySearchMapper;

    @Mock
    private SearchMapper searchMapper;

    @Mock
    private AvailabilitySearchProducerPort availabilitySearchProducerPort;

    @Mock
    private AvailabilitySearchDomainService availabilitySearchDomainService;

    @InjectMocks
    private AvailabilitySearchApplicationServiceImpl sut;
    @Test
    public void testGetBySearchId_WhenSearchIdExists_ShouldReturnAvailabilitySearchDto() {
        String searchId = "sampleSearchId";
        AvailabilitySearchDocument availabilitySearchDocument = new AvailabilitySearchDocument();

        when(jpaAvailabilitySearchRepository.findBySearchId(searchId)).thenReturn(Optional.of(availabilitySearchDocument));

        AvailabilitySearchDto availabilitySearchDto = buildAvailabilitySearchDto();

        when(availabilitySearchMapper.toDto(any(AvailabilitySearchDocument.class))).thenReturn(availabilitySearchDto);

        Optional<AvailabilitySearchDto> result = sut.getBySearchId(searchId);

        assertTrue(result.isPresent());
        assertEquals(availabilitySearchDto, result.get());

        verify(jpaAvailabilitySearchRepository, times(1)).findBySearchId(searchId);
        verify(availabilitySearchMapper, times(1)).toDto(availabilitySearchDocument);
    }

    @Test
    public void testGetBySearchId_WhenSearchIdDoesNotExist_ShouldReturnEmptyOptional() {
        String searchId = "nonExistentSearchId";
        when(jpaAvailabilitySearchRepository.findBySearchId(searchId)).thenReturn(Optional.empty());

        Optional<AvailabilitySearchDto> result = sut.getBySearchId(searchId);

        assertFalse(result.isPresent());

        verify(jpaAvailabilitySearchRepository, times(1)).findBySearchId(searchId);
        verify(availabilitySearchMapper, never()).toDto(any(AvailabilitySearchDocument.class));
    }

    @Test
    public void testRegisterSearch_ShouldRegisterSearchAndSendMessage() {
        SearchDto searchDto = buildSearchDto();
        Search search = buildSearch();

        AvailabilitySearch availabilitySearch = buildAvailabilitySearch();

        when(searchMapper.toDomain(searchDto)).thenReturn(search);
        when(availabilitySearchDomainService.build(search)).thenReturn(availabilitySearch);

        SearchIdResponseDto result = sut.registerSearch(searchDto);

        assertNotNull(result);
        assertNotNull(result.getSearchId());

        verify(searchMapper, times(1)).toDomain(searchDto);
        verify(availabilitySearchDomainService, times(1)).build(search);
        verify(availabilitySearchProducerPort, times(1)).send(searchDto);
    }

    @Test
    public void testPersistSearch_WhenAvailabilitySearchDocumentExists_ShouldIncrementCountAndSave() {
        SearchDto searchDto = buildSearchDto();
        SearchDocument searchDocument = buildSearchDocument();
        AvailabilitySearchDocument availabilitySearchDocument = buildAvailabilitySearchDocument();

        when(searchMapper.toDocument(searchDto)).thenReturn(searchDocument);
        when(searchMapper.toDomain(searchDto)).thenReturn(buildSearch());
        when(jpaAvailabilitySearchRepository.findBySearchDocument(searchDocument)).thenReturn(Optional.of(availabilitySearchDocument));
        when(availabilitySearchMapper.toDomain(availabilitySearchDocument)).thenReturn(buildAvailabilitySearch());
        when(availabilitySearchMapper.toDocument(any(AvailabilitySearch.class),any())).thenReturn(buildAvailabilitySearchDocument());

        sut.persistSearch(searchDto);

        verify(searchMapper, times(1)).toDocument(searchDto);
        verify(searchMapper, times(1)).toDomain(searchDto);
        verify(jpaAvailabilitySearchRepository, times(1)).findBySearchDocument(searchDocument);
        verify(availabilitySearchMapper, times(1)).toDomain(availabilitySearchDocument);
        verify(jpaAvailabilitySearchRepository, times(1)).save(any(AvailabilitySearchDocument.class));
    }

    @Test
    public void testPersistSearch_WhenAvailabilitySearchDocumentDoesNotExist_ShouldCreateNewAndSave() {
        SearchDto searchDto = buildSearchDto();
        SearchDocument searchDocument = buildSearchDocument();
        AvailabilitySearch availabilitySearch = buildAvailabilitySearch();

        when(searchMapper.toDocument(searchDto)).thenReturn(searchDocument);
        when(searchMapper.toDomain(searchDto)).thenReturn(buildSearch());
        when(jpaAvailabilitySearchRepository.findBySearchDocument(searchDocument)).thenReturn(Optional.empty());
        when(availabilitySearchDomainService.build(any(Search.class))).thenReturn(availabilitySearch);
        when(availabilitySearchMapper.toDocument(any(AvailabilitySearch.class),any())).thenReturn(buildAvailabilitySearchDocument());

        sut.persistSearch(searchDto);

        verify(searchMapper, times(1)).toDocument(searchDto);
        verify(searchMapper, times(1)).toDomain(searchDto);
        verify(jpaAvailabilitySearchRepository, times(1)).findBySearchDocument(any(SearchDocument.class));
        verify(availabilitySearchDomainService, times(1)).build(any(Search.class));
        verify(jpaAvailabilitySearchRepository, times(1)).save(any(AvailabilitySearchDocument.class));
    }

    private AvailabilitySearchDto buildAvailabilitySearchDto(){
        return new AvailabilitySearchDto("SearchId", buildSearchDto(), 1);
    }

    private SearchDto buildSearchDto() {
        return new SearchDto("HotelId", LocalDate.now(), LocalDate.now(), Collections.singletonList(1));
    }

    private AvailabilitySearch buildAvailabilitySearch(){
        return new AvailabilitySearch("SearchId", buildSearch(), 1);
    }

    private Search buildSearch(){
        return new Search("HotelId", LocalDate.now(), LocalDate.now(), Collections.singletonList(1));
    }

    private AvailabilitySearchDocument buildAvailabilitySearchDocument(){
        return new AvailabilitySearchDocument("id","SearchId", buildSearchDocument(), 1);
    }

    private SearchDocument buildSearchDocument() {
        return new SearchDocument("HotelId", LocalDate.now(), LocalDate.now(), Collections.singletonList(1));
    }
}