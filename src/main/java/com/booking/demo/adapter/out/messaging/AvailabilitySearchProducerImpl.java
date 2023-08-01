package com.booking.demo.adapter.out.messaging;

import com.booking.demo.application.dto.SearchDto;
import com.booking.demo.application.service.common.JsonConverter;
import com.booking.demo.port.out.messaging.AvailabilitySearchProducerPort;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AvailabilitySearchProducerImpl implements AvailabilitySearchProducerPort {
    @Value("${kafka.topic.availabilitysearch}")
    private String availabilitySearchTopic;
    private final Producer<String, String> bookingProducer;
    private final JsonConverter jsonConverter;

    public AvailabilitySearchProducerImpl(Producer<String, String> bookingProducer, JsonConverter jsonConverter) {
        this.bookingProducer = bookingProducer;
        this.jsonConverter = jsonConverter;
    }

    @Override
    public void publish(SearchDto searchDto) {
        String json = jsonConverter.convertToJson(searchDto);
        bookingProducer.send(getRecord(json));
    }

    private ProducerRecord<String, String> getRecord(String json){
        return new ProducerRecord<>(availabilitySearchTopic, json);
    }

}