package com.booking.demo.port.in.web;

import com.booking.demo.adapter.out.db.repository.JpaAvailabilitySearchRepository;
import com.booking.demo.application.dto.AvailabilitySearchDto;
import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.dto.SearchIdResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AvailabilitySearchApiPortTest {
    private static final String SEARCH_ID = "4738c139bf2dbfa4da54901bb26f0b1784b4611e0936c70320d0de5e20269ea3";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private JpaAvailabilitySearchRepository jpaAvailabilitySearchRepository;

    @Test
    @Order(1)
    void givenNonExistentSearchId_whenGetBySearchId_thenAvailabilitySearchReturned() throws Exception {
        jpaAvailabilitySearchRepository.deleteBySearchId(SEARCH_ID);

        MockHttpServletResponse response = getCountBySearchIdHttpResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
    @Test
    @Order(2)
    void givenSearchDto_whenFirtsRegisterSearch_thenSearchIdReturned() throws Exception {
        MockHttpServletResponse response = registerSearchHttpResponse();

        registerSearchAsserts(response);
    }

    private MockHttpServletResponse registerSearchHttpResponse() throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                        .post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(buildSearchDto())))
                .andReturn().getResponse();
    }

    @Test
    @Order(3)
    void givenExistentSearchId_whenGetBySearchId_thenAvailabilitySearchReturnedWithCountOne() throws Exception {
        Thread.sleep(2000);

        MockHttpServletResponse response = getCountBySearchIdHttpResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        AvailabilitySearchDto availabilitySearchDto = mapper.readValue(response.getContentAsString(), AvailabilitySearchDto.class);
        assertEquals(SEARCH_ID, availabilitySearchDto.getSearchId());
        assertEquals(1,availabilitySearchDto.getCount());
    }

    @Test
    @Order(4)
    void givenSearchDto_whenSecondRegisterSearch_thenSearchIdReturned() throws Exception {
        Thread.sleep(2000);

        MockHttpServletResponse response = registerSearchHttpResponse();

        registerSearchAsserts(response);
    }

    private void registerSearchAsserts(MockHttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        SearchIdResponseDto searchIdResponseDto = mapper.readValue(response.getContentAsString(), SearchIdResponseDto.class);
        assertEquals(SEARCH_ID, searchIdResponseDto.getSearchId());
    }

    @Test
    @Order(5)
    void givenExistentSearchId_whenGetBySearchId_thenAvailabilitySearchReturnedWithCountTwo() throws Exception {
        Thread.sleep(2000);

        MockHttpServletResponse response = getCountBySearchIdHttpResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        AvailabilitySearchDto availabilitySearchDto = mapper.readValue(response.getContentAsString(), AvailabilitySearchDto.class);
        assertEquals(SEARCH_ID, availabilitySearchDto.getSearchId());
        assertEquals(2,availabilitySearchDto.getCount());
    }

    private MockHttpServletResponse getCountBySearchIdHttpResponse() throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                        .get("/count")
                        .param("searchId", SEARCH_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    private SearchDto buildSearchDto() {
        return new SearchDto("HotelId", LocalDate.of(2023,8,3), LocalDate.of(2023,8,4),
                Collections.singletonList(1));
    }

}