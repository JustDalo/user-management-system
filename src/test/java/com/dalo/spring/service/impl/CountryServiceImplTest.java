package com.dalo.spring.service.impl;

import com.dalo.spring.dao.CountryRepository;
import com.dalo.spring.model.Country;
import com.dalo.spring.service.impl.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class CountryServiceImplTest {
    private CountryServiceImpl countryService;

    @Mock
    private CountryRepository countryRepository;

    private static final Country BELARUS_COUNTRY = new Country(1L, "Belarus");
    private static final Country UNKNOWN_SOURCE_COUNTRY = new Country(2L, "Unknown source");

    @BeforeEach
    public void setUp() {
        countryService = new CountryServiceImpl(countryRepository);
    }

    @Test
    void getCountryByLocalHostIPShouldReturnBelarusTest() {

        Mockito.when(countryRepository.findByName("Belarus")).thenReturn(Optional.of(BELARUS_COUNTRY));
        Country actual = countryService.getCountryByIP("5.100.193.158");

        assertEquals(BELARUS_COUNTRY, actual);
    }

    @Test
    void getCountryByLocalHostIPShouldReturnUnknownSourceTest() {

        Mockito.when(countryRepository.findByName(UNKNOWN_SOURCE_COUNTRY.getName())).thenReturn(Optional.of(UNKNOWN_SOURCE_COUNTRY));
        Country actual = countryService.getCountryByIP("127.0.0.1");

        assertEquals(UNKNOWN_SOURCE_COUNTRY, actual);
    }
}