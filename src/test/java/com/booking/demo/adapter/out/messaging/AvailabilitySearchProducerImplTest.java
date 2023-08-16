package com.booking.demo.adapter.out.messaging;

import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.service.common.JsonConverter;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailabilitySearchProducerImplTest {
    @Mock
    private Producer<String, String> bookingProducer;
    @Mock
    private JsonConverter jsonConverter;
    @InjectMocks
    private AvailabilitySearchProducerImpl sut;

    @Test
    void testSend_whenHaveASearch_thenSendMessageToKafkaTopic() {
        sut.availabilitySearchTopic="topic";
        String json = buildSearchJson();
        SearchDto searchDto = buildSearchDto();
        when(jsonConverter.convertToJson(any(SearchDto.class))).thenReturn(json);

        sut.send(searchDto);

        verify(jsonConverter).convertToJson(searchDto);
        verify(bookingProducer).send(new ProducerRecord<>(sut.availabilitySearchTopic, json));
    }
    private SearchDto buildSearchDto() {
        return new SearchDto("HotelId", LocalDate.now(), LocalDate.now(), Collections.singletonList(1));
    }

    private String buildSearchJson(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"hotelId\": \"HotelId\",");
        sb.append("\"checkIn\": \"2023-08-03\",");
        sb.append("\"checkOut\": \"2023-08-03\",");
        sb.append("\"ages\": [30]");
        sb.append("}");
        return sb.toString();
    }
}