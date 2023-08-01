package com.booking.demo.adapter.in.web;

import com.booking.demo.application.dto.AvailabilitySearchDto;
import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.dto.SearchIdResponseDto;
import com.booking.demo.application.service.availabilitysearch.AvailabilitySearchUseCases;
import com.booking.demo.port.in.web.AvailabilitySearchApiPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvailabilitySearchController implements AvailabilitySearchApiPort {
    private final AvailabilitySearchUseCases availabilitySearchUseCases;

    public AvailabilitySearchController(AvailabilitySearchUseCases availabilitySearchUseCases) {
        this.availabilitySearchUseCases = availabilitySearchUseCases;
    }

    @GetMapping(value = "/count")
    public ResponseEntity<AvailabilitySearchDto> getBySearchId(@RequestParam(value = "searchId") String searchId){
        return availabilitySearchUseCases.getBySearchId(searchId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping(value = "/search")
    public ResponseEntity<SearchIdResponseDto> registerSearch(@RequestBody SearchDto searchDto) {
        return ResponseEntity.ok(availabilitySearchUseCases.registerSearch(searchDto));
    }
}
