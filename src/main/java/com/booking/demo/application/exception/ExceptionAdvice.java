package com.booking.demo.application.exception;

import com.booking.demo.application.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(HttpServletRequest request, Exception e) {
        return new ResponseEntity<>(buildError(request, "Internal server error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDto> handleMissingServletRequestParameterException(HttpServletRequest request,
                                                                                  MissingServletRequestParameterException e) {
      return new ResponseEntity<>(buildError(request, "Missing request parameter: " + e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(HttpServletRequest request, ValidationException e) {
        return new ResponseEntity<>(buildError(request, "Validation error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingJsonProcessingException.class)
    public ResponseEntity<ErrorDto> handleBookingJsonProcessingException(HttpServletRequest request, BookingJsonProcessingException e) {
        return new ResponseEntity<>(buildError(request, "Error during Jackson writeValueAsString: " + e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BookingNoSuchAlgorithmException.class)
    public ResponseEntity<ErrorDto> handleBookingNoSuchAlgorithmException(HttpServletRequest request, BookingNoSuchAlgorithmException e) {
        return new ResponseEntity<>(buildError(request, "Error during MessageDigest getInstance: " + e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDto buildError(HttpServletRequest request, String message){
        return new ErrorDto(request.getRequestURI(),message,LocalDateTime.now());
    }

}
