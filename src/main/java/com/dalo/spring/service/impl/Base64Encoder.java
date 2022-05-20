package com.dalo.spring.service.impl;

import com.dalo.spring.service.EncoderService;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class Base64Encoder implements EncoderService {
    @Override
    public byte[] encodeToByte(String baseString) {
        if (baseString != null) {
            return Base64.getDecoder().decode(baseString);
        }
        return null;
    }

    @Override
    public String encodeToString(byte[] image) {
        if (image != null) {
            return Base64.getEncoder().encodeToString(image);
        }
        return null;
    }
}
