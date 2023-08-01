package com.booking.demo.port.in.web;

import com.booking.demo.application.dto.AvailabilitySearchDto;
import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.dto.SearchIdResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Availability Search Managing")
@RequestMapping
public interface AvailabilitySearchApiPort {


    @Operation(summary = "Returns the Availability Search Count by searchId")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Availability Search count returned"),
            @ApiResponse(responseCode = "204", description = "Availability Search not exists"),
            @ApiResponse(responseCode = "400", description = "Bad request param")
    })
    ResponseEntity<AvailabilitySearchDto> getBySearchId(String searchId);
    @Operation(summary = "Register the search and return the searchId")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search register and searchId returned"),
            @ApiResponse(responseCode = "400", description = "Bad request param")
    })
    ResponseEntity<SearchIdResponseDto> registerSearch(SearchDto searchDto);

}
