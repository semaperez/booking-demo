package com.booking.demo.adapter.in.messaging;

import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.service.availabilitysearch.AvailabilitySearchUseCases;
import com.booking.demo.application.service.common.JsonConverter;
import com.booking.demo.port.in.messaging.AvailabilitySearchConsumerPort;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

@Service
public class AvailabilitySearchConsumerImpl implements AvailabilitySearchConsumerPort {
    @Value("${kafka.topic.availabilitysearch}")
    private String availabilitySearchTopic;
    @Value("${kafka.consumer.poolTime}")
    private Long poolTime;
    private final Consumer<String, String> bookingConsumer;
    private final JsonConverter jsonConverter;
    private final AvailabilitySearchUseCases availabilitySearchUseCases;

    public AvailabilitySearchConsumerImpl(Consumer<String, String> bookingConsumer, JsonConverter jsonConverter,
                                          AvailabilitySearchUseCases availabilitySearchUseCases) {
        this.bookingConsumer = bookingConsumer;
        this.jsonConverter = jsonConverter;
        this.availabilitySearchUseCases = availabilitySearchUseCases;
    }

    @Override
    @PostConstruct
    public void startConsumer() {
        bookingConsumer.subscribe(Collections.singletonList(availabilitySearchTopic));
        new Thread(() -> {
            while (true) {
                ConsumerRecords<String, String> records = bookingConsumer.poll(Duration.ofMillis(poolTime));
                records.forEach(r -> {
                    SearchDto searchDto = jsonConverter.convertToObject(r.value(), SearchDto.class);
                    availabilitySearchUseCases.persistSearch(searchDto);
                });
            }
        }).start();
    }
}
