package com.booking.demo.adapter.out.messaging;

import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.port.out.messaging.AvailabilitySearchProducerPort;
import org.springframework.beans.factory.annotation.Value;

public class AvailabilitySearchProducerImpl implements AvailabilitySearchProducerPort {
    @Value("${kafka.topic.availabilitysearch}")
    private String availabilitySearchTopic;
    @Override
    public void publish(String topic, String key, SearchDto searchDto) {

    }
}
