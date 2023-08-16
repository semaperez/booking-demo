package com.booking.demo.domain.service;

import com.booking.demo.domain.model.AvailabilitySearch;
import com.booking.demo.domain.model.Search;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailabilitySearchDomainServiceImplTest {
    @InjectMocks
    AvailabilitySearchDomainServiceImpl sut;
    @Mock
    HashGenerator hashGenerator;
    @Test
    void testBuild() {
        when(hashGenerator.generateHashFromObject(any(Search.class))).thenReturn("hashKey");
        Search search = buildSearch();

        AvailabilitySearch actualBuildResult = sut.build(search);

        assertEquals(0, actualBuildResult.getCount());
        assertEquals("hashKey", actualBuildResult.getSearchId());
        assertEquals(search, actualBuildResult.getSearch());
    }

    private Search buildSearch(){
        return new Search("HotelId", LocalDate.of(2023,8,3), LocalDate.of(2023,8,4),
                Collections.singletonList(1));
    }
}

