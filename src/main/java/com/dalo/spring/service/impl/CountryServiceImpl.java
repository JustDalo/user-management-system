package com.dalo.spring.service.impl;

import com.dalo.spring.dao.CountryRepository;
import com.dalo.spring.model.Country;
import com.dalo.spring.service.CountryService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Optional;
import java.util.Properties;

@Service
public class CountryServiceImpl implements CountryService {
    private static final String UNKNOWN_SOURCE = "Unknown source";
    private DatabaseReader dbReader;
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;

        try {
            InputStream inputStream = getClass().getResourceAsStream("/GeoLite2-Country.mmdb");
            dbReader = new DatabaseReader.Builder(inputStream).build();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country getCountryByIP(String ip) {
        String countryName;
        CountryResponse response;
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            response = dbReader.country(ipAddress);
            countryName = response.getCountry().getName();
        } catch (GeoIp2Exception | IOException ex) {
            countryName = UNKNOWN_SOURCE;
        }

        Optional<Country> country = countryRepository.findByName(countryName);
        String finalCountryName = countryName;
        return country.orElseGet(() -> createCountry(new Country(finalCountryName)));
    }
}
