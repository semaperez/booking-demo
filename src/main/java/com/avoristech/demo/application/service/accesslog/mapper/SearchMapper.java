package com.avoristech.demo.application.service.accesslog.mapper;

import com.avoristech.demo.adapter.out.db.model.Search;
import com.avoristech.demo.application.dto.SearchDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchMapper {
    Search toDocument(final SearchDto dto);
}
