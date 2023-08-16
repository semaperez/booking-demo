package com.booking.demo.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HashGeneratorTest {
    @InjectMocks
    private HashGenerator hashGenerator;

    @Test
    void testGenerateHashFromObject() {
        assertEquals("62a6da8735c18e2d66fe5de3dc5440252a1da49e3cbaa7c1d2d5068ad73ffba0",
                hashGenerator.generateHashFromObject("Object"));
    }
    @Test
    void testGenerateHashFromNull() {
        assertEquals(null, hashGenerator.generateHashFromObject(null));
    }
}

