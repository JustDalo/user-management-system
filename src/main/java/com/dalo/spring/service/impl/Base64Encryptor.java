package com.dalo.spring.service.impl;

import com.dalo.spring.service.CryptService;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class Base64Encryptor implements CryptService {
    @Override
    public byte[] cryptToByte(String baseString) {
        return Base64.getDecoder().decode(baseString);
    }

    @Override
    public String cryptToString(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }
}
