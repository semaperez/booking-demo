package com.booking.demo.port.out.messaging;

import com.booking.demo.application.dto.SearchDto;
import org.springframework.stereotype.Service;

@Service
public interface AvailabilitySearchProducerPort {
    void publish(String topic, String key, SearchDto searchDto);
}