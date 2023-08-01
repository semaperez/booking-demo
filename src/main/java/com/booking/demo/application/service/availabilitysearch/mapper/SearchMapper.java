package com.booking.demo.application.service.availabilitysearch.mapper;

import com.booking.demo.adapter.out.db.model.Search;
import com.booking.demo.application.dto.SearchDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchMapper {
    Search toDocument(final SearchDto dto);
}
