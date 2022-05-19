package com.dalo.spring.service;

public interface CryptService {
    byte[] cryptToByte(String baseString);
    String cryptToString(byte[] image);

}
