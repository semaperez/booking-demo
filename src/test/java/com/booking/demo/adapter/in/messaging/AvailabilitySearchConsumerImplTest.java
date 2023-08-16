package com.booking.demo.adapter.in.messaging;

import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.service.availabilitysearch.AvailabilitySearchUseCases;
import com.booking.demo.application.service.common.JsonConverter;
import org.apache.kafka.clients.consumer.Consumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailabilitySearchConsumerImplTest {
    @Mock
    private Consumer<String, String> bookingConsumer;

    @Mock
    private JsonConverter jsonConverter;

    @Mock
    private AvailabilitySearchUseCases availabilitySearchUseCases;

    @InjectMocks
    private AvailabilitySearchConsumerImpl sut;

    @Test
    void testStartConsumer_whenHaveARecord_thenSearchIsPersist() throws ExecutionException, InterruptedException {
        SearchDto searchDto = new SearchDto("HotelId", LocalDate.now(), LocalDate.now(), Collections.singletonList(1));
        when(jsonConverter.convertToObject(anyString(), eq(SearchDto.class))).thenReturn(searchDto);

        sut.listen(buildSearchJson());

        verify(availabilitySearchUseCases, times(1)).persistSearch(searchDto);
    }

    private String buildSearchJson(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"hotelId\": \"HotelId\",");
        sb.append("\"checkIn\": \"2023-08-03\",");
        sb.append("\"checkOut\": \"2023-08-04\",");
        sb.append("\"ages\": [30]");
        sb.append("}");
        return sb.toString();
    }
}