package com.booking.demo.application.service.availabilitysearch.mapper;

import com.booking.demo.adapter.out.db.model.AvailabilitySearchDocument;
import com.booking.demo.application.dto.AvailabilitySearchDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SearchMapper.class})
public interface AvailabilitySearchMapper {
    AvailabilitySearchDto toDto(final AvailabilitySearchDocument document);
}
