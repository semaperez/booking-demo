package com.avoristech.demo.application.service.accesslog.mapper;

import com.avoristech.demo.adapter.out.db.model.AccessLogCountDocument;
import com.avoristech.demo.application.dto.AccessLogCountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SearchMapper.class})
public interface AccessLogMapper {
    AccessLogCountDto toDto(final AccessLogCountDocument document);
}
