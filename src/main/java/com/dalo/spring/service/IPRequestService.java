package com.dalo.spring.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface IPRequestService {
    String getClientIP(HttpServletRequest request);
}
