package com.booking.demo.application.service.availabilitysearch;

import com.booking.demo.application.dto.AvailabilitySearchDto;
import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.dto.SearchIdResponseDto;

import java.util.Optional;

public interface AvailabilitySearchUseCases {
    Optional<AvailabilitySearchDto> getBySearchId(String searchId);
    SearchIdResponseDto registerSearch(SearchDto searchDto);
}
