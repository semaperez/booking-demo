package com.booking.demo.application.service.availabilitysearch.mapper;

import com.booking.demo.adapter.out.db.model.AvailabilitySearchDocument;
import com.booking.demo.application.dto.AvailabilitySearchDto;
import com.booking.demo.domain.model.AvailabilitySearch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SearchMapper.class})
public interface AvailabilitySearchMapper {
    AvailabilitySearchDto toDto(final AvailabilitySearchDocument document);
    AvailabilitySearch toDomain(final AvailabilitySearchDocument document);
    @Mapping(target = "id", source = "documentId")
    AvailabilitySearchDocument toDocument(final AvailabilitySearch domain, String documentId);
}
