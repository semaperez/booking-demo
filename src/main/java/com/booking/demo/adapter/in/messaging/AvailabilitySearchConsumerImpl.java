package com.booking.demo.adapter.in.messaging;

import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.service.availabilitysearch.AvailabilitySearchUseCases;
import com.booking.demo.application.service.common.JsonConverter;
import com.booking.demo.port.in.messaging.AvailabilitySearchConsumerPort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AvailabilitySearchConsumerImpl implements AvailabilitySearchConsumerPort {
    private final JsonConverter jsonConverter;
    private final AvailabilitySearchUseCases availabilitySearchUseCases;

    public AvailabilitySearchConsumerImpl(JsonConverter jsonConverter,
                                          AvailabilitySearchUseCases availabilitySearchUseCases) {
        this.jsonConverter = jsonConverter;
        this.availabilitySearchUseCases = availabilitySearchUseCases;
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.availabilitysearch}")
    public void listen(String message) {
        SearchDto searchDto = jsonConverter.convertToObject(message, SearchDto.class);
        availabilitySearchUseCases.persistSearch(searchDto);
    }
}
