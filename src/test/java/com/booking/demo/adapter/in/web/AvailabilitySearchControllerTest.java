package com.booking.demo.adapter.in.web;

import com.booking.demo.application.dto.AvailabilitySearchDto;
import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.dto.SearchIdResponseDto;
import com.booking.demo.application.service.availabilitysearch.AvailabilitySearchUseCases;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AvailabilitySearchControllerTest {
    @Mock
    private AvailabilitySearchUseCases availabilitySearchUseCases;

    @InjectMocks
    private AvailabilitySearchController sut;

    @Test
    void testGetBySearchId_WhenSearchIdExists_ReturnsAvailabilitySearchDto() {
        AvailabilitySearchDto availabilitySearchDto = buildAvailabilitySearchDto();

        when(availabilitySearchUseCases.getBySearchId(anyString())).thenReturn(Optional.of(availabilitySearchDto));

        ResponseEntity<AvailabilitySearchDto> responseEntity = sut.getBySearchId("searchIdHash");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(availabilitySearchDto, responseEntity.getBody());
    }

    @Test
    void testGetBySearchId_WhenSearchIdDoesNotExist_ReturnsNoContent() {
        String searchId = "nonExistentSearchId";

        when(availabilitySearchUseCases.getBySearchId(searchId)).thenReturn(Optional.empty());

        ResponseEntity<AvailabilitySearchDto> responseEntity = sut.getBySearchId(searchId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void testRegisterSearch_ReturnsSearchIdResponseDto() {
        SearchDto searchDto = buildSearchDto();

        SearchIdResponseDto searchIdResponseDto = new SearchIdResponseDto("searchIdHash");

        when(availabilitySearchUseCases.registerSearch(any(SearchDto.class))).thenReturn(searchIdResponseDto);

        ResponseEntity<SearchIdResponseDto> responseEntity = sut.registerSearch(searchDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(searchIdResponseDto, responseEntity.getBody());
    }

    private AvailabilitySearchDto buildAvailabilitySearchDto(){
        return new AvailabilitySearchDto("SearchId", buildSearchDto(), 1);
    }

    private SearchDto buildSearchDto() {
        return new SearchDto("HotelId", LocalDate.now(), LocalDate.now(), Collections.singletonList(1));
    }
}