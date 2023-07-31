package com.avoristech.demo.application.service.accesslog;

import com.avoristech.demo.application.dto.AccessLogCountDto;
import com.avoristech.demo.application.dto.SearchDto;
import com.avoristech.demo.application.dto.SearchIdResponseDto;

import java.util.Optional;

public interface AccessLogUseCases {
    Optional<AccessLogCountDto> getBySearchId(String searchId);
    SearchIdResponseDto registerSearch(SearchDto searchDto);
}
