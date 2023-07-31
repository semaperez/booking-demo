package com.avoristech.demo.adapter.in.web;

import com.avoristech.demo.application.dto.AccessLogCountDto;
import com.avoristech.demo.application.dto.SearchDto;
import com.avoristech.demo.application.dto.SearchIdResponseDto;
import com.avoristech.demo.application.service.accesslog.AccessLogUseCases;
import com.avoristech.demo.port.in.web.AccessLogApiPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessLogController implements AccessLogApiPort {
    private AccessLogUseCases accessLogUseCases;

    public AccessLogController(AccessLogUseCases accessLogUseCases) {
        this.accessLogUseCases = accessLogUseCases;
    }

    @GetMapping(value = "/count")
    public ResponseEntity<AccessLogCountDto> getBySearchId(@RequestParam(value = "searchId") String searchId){
        return accessLogUseCases.getBySearchId(searchId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping(value = "/search")
    public ResponseEntity<SearchIdResponseDto> registerSearch(@RequestBody SearchDto searchDto) {
        return ResponseEntity.ok(accessLogUseCases.registerSearch(searchDto));
    }
}
