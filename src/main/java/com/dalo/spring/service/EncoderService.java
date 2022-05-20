package com.dalo.spring.service;

public interface EncoderService {
    byte[] encodeToByte(String baseString);

    String encodeToString(byte[] image);
}
