package com.dalo.spring.service.impl;

import com.dalo.spring.service.EncoderService;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class Base64Encoder implements EncoderService {
    @Override
    public byte[] encodeToByte(String baseString) {
        return Base64.getDecoder().decode(baseString);
    }

    @Override
    public String encodeToString(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }
}
