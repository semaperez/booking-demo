package com.booking.demo.application.service.common;

import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.exception.BookingJsonProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonConverterTest {
    @InjectMocks
    private JsonConverter sut;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    void testConvertToJson() throws Exception {
        SearchDto objectToConvert = buildSearchDto();
        String expectedJson = "{'key':'value'}";

        when(objectMapper.writeValueAsString(objectToConvert)).thenReturn(expectedJson);

        String actualJson = sut.convertToJson(objectToConvert);

        assertEquals(expectedJson, actualJson);
        verify(objectMapper).writeValueAsString(objectToConvert);
    }

    @Test
    void testConvertToObject() throws Exception {
        String jsonToConvert = buildSearchJson();
        SearchDto expectedObject = buildSearchDto();

        when(objectMapper.readValue(jsonToConvert, SearchDto.class)).thenReturn(expectedObject);

        Object actualObject = sut.convertToObject(jsonToConvert, SearchDto.class);

        assertEquals(expectedObject, actualObject);
        verify(objectMapper).readValue(jsonToConvert, SearchDto.class);
    }

    @Test
    void testConvertToJson_JsonProcessingException() throws Exception {
        Object objectToConvert = new Object();

        when(objectMapper.writeValueAsString(objectToConvert)).thenThrow(JsonProcessingException.class);

        assertThrows(BookingJsonProcessingException.class, () -> sut.convertToJson(objectToConvert));
        verify(objectMapper).writeValueAsString(objectToConvert);
    }

    @Test
    void testConvertToObject_JsonProcessingException() throws Exception {
        String jsonToConvert = "{'key':'value'}";
        Class<?> valueType = Object.class;

        when(objectMapper.readValue(jsonToConvert, valueType)).thenThrow(JsonProcessingException.class);

        assertThrows(BookingJsonProcessingException.class, () -> sut.convertToObject(jsonToConvert, valueType));
        verify(objectMapper).readValue(jsonToConvert, valueType);
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

    private SearchDto buildSearchDto() {
        return new SearchDto("HotelId", LocalDate.of(2023,8,3), LocalDate.of(2023,8,3),
                Collections.singletonList(30));
    }

}


