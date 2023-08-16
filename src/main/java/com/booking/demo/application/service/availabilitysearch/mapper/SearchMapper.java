package com.booking.demo.application.service.availabilitysearch.mapper;

import com.booking.demo.adapter.out.db.model.SearchDocument;
import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.domain.model.Search;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchMapper {
    SearchDocument toDocument(final SearchDto dto);
    SearchDocument toDocument(final Search domain);
    Search toDomain(final SearchDto dto);
    Search toDomain(final SearchDocument document);
    SearchDto toDto(final SearchDocument document);
}
