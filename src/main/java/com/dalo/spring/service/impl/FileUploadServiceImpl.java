package com.dalo.spring.service.impl;

import com.dalo.spring.service.FileUploadService;
import com.dalo.spring.service.utils.TransferMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public byte[] uploadFile(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MultipartFile sentFile(byte[] imageContent) {
        return new TransferMultipartFile(imageContent);
    }
}
