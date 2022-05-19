package com.dalo.spring.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    byte[] uploadFile(MultipartFile multipartFile);
    MultipartFile sentFile(byte[] imageContent);
}
