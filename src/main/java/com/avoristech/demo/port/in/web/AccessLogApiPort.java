package com.avoristech.demo.port.in.web;

import com.avoristech.demo.application.dto.AccessLogCountDto;
import com.avoristech.demo.application.dto.SearchDto;
import com.avoristech.demo.application.dto.SearchIdResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Access Log Managing")
@RequestMapping
public interface AccessLogApiPort {


    @Operation(summary = "Returns the AccessLogCount by searchId")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AccessLog count returned"),
            @ApiResponse(responseCode = "204", description = "AccessLog count not exists"),
            @ApiResponse(responseCode = "400", description = "Bad request param")
    })
    ResponseEntity<AccessLogCountDto> getBySearchId(String searchId);
    @Operation(summary = "Register the search and return the searchId")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search register and searchId returned"),
            @ApiResponse(responseCode = "400", description = "Bad request param")
    })
    ResponseEntity<SearchIdResponseDto> registerSearch(SearchDto searchDto);

}
