package com.dalo.spring.service;

import com.dalo.spring.model.Country;
import org.springframework.stereotype.Service;

@Service
public interface CountryService {
    Country getCountryByIP(String ip);
}
