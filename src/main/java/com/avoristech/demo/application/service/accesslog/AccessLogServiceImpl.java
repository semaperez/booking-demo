package com.avoristech.demo.application.service.accesslog;

import com.avoristech.demo.adapter.out.db.model.AccessLogCountDocument;
import com.avoristech.demo.adapter.out.db.model.Search;
import com.avoristech.demo.adapter.out.db.repository.JpaAccessLogCountRepository;
import com.avoristech.demo.application.dto.AccessLogCountDto;
import com.avoristech.demo.application.dto.SearchDto;
import com.avoristech.demo.application.dto.SearchIdResponseDto;
import com.avoristech.demo.application.service.accesslog.mapper.AccessLogMapper;
import com.avoristech.demo.application.service.accesslog.mapper.SearchMapper;
import com.avoristech.demo.application.service.common.HashGenerator;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccessLogServiceImpl implements AccessLogUseCases {
    private JpaAccessLogCountRepository jpaAccessLogCountRepository;
    private AccessLogMapper accessLogMapper;
    private SearchMapper searchMapper;
    private HashGenerator hashGenerator;

    public AccessLogServiceImpl(JpaAccessLogCountRepository jpaAccessLogCountRepository, AccessLogMapper accessLogMapper, SearchMapper searchMapper
            , HashGenerator hashGenerator) {
        this.jpaAccessLogCountRepository = jpaAccessLogCountRepository;
        this.accessLogMapper = accessLogMapper;
        this.searchMapper = searchMapper;
        this.hashGenerator = hashGenerator;
    }

    @Override
    public Optional<AccessLogCountDto> getBySearchId(String searchId) {
        return jpaAccessLogCountRepository.findBySearchId(searchId).map(accessLogMapper::toDto);
    }

    @Override
    public SearchIdResponseDto registerSearch(SearchDto searchDto) {
        Search search = searchMapper.toDocument(searchDto);

        String hash = hashGenerator.generateHashFromObject(search);

        AccessLogCountDocument accessLogCountDocument =
                jpaAccessLogCountRepository.findBySearch(search).stream().findFirst().orElse(new AccessLogCountDocument());
        accessLogCountDocument.setSearchId(hash);
        accessLogCountDocument.setSearch(search);
        accessLogCountDocument.setCount(Objects.requireNonNullElse(accessLogCountDocument.getCount(),0)+1);
        jpaAccessLogCountRepository.save(accessLogCountDocument);

        return new SearchIdResponseDto(hash);
    }

}
