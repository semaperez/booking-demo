package com.booking.demo.application.exception;

import com.booking.demo.application.dto.ErrorDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ExceptionAdviceTest {
    @Test
    void testHandleException() {
        ExceptionAdvice exceptionAdvice = new ExceptionAdvice();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorDto> result = exceptionAdvice.handleException(request, new Exception());
        assertEquals(500, result.getStatusCodeValue());
        assertEquals("Internal server error: null", result.getBody().getErrorMessage());
    }

    @Test
    void testHandleMissingServletRequestParameterException() {
        ExceptionAdvice exceptionAdvice = new ExceptionAdvice();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorDto> result = exceptionAdvice
                .handleMissingServletRequestParameterException(request,
                        new MissingServletRequestParameterException("Parameter Name", "Parameter Type"));
        assertEquals(400, result.getStatusCodeValue());
        assertEquals("Missing request parameter: Required request parameter 'Parameter Name' for method parameter type"
                + " Parameter Type is not present", result.getBody().getErrorMessage());
    }

    @Test
    void testHandleValidationException() {
        ExceptionAdvice exceptionAdvice = new ExceptionAdvice();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorDto> result = exceptionAdvice
                .handleValidationException(request, new ValidationException("An error occurred"));
        assertEquals(400, result.getStatusCodeValue());
        assertEquals("Validation error: An error occurred", result.getBody().getErrorMessage());
    }

   @Test
    void testHandleBookingJsonProcessingException() {
        ExceptionAdvice exceptionAdvice = new ExceptionAdvice();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorDto> result = exceptionAdvice
                .handleBookingJsonProcessingException(request,
                        new BookingJsonProcessingException(mock(JsonProcessingException.class)));
        assertEquals(500, result.getStatusCodeValue());
    }

    @Test
    void testHandleBookingNoSuchAlgorithmException() {
        ExceptionAdvice exceptionAdvice = new ExceptionAdvice();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorDto> result = exceptionAdvice
                .handleBookingNoSuchAlgorithmException(request,
                        new BookingNoSuchAlgorithmException(new NoSuchAlgorithmException()));
        assertTrue(result.hasBody());
        assertTrue(result.getHeaders().isEmpty());
        assertEquals(500, result.getStatusCodeValue());
        assertEquals("Error during MessageDigest getInstance: java.security.NoSuchAlgorithmException",
                result.getBody().getErrorMessage());
    }
}

