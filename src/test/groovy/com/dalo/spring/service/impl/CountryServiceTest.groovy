package com.dalo.spring.service.impl

import com.dalo.spring.dao.CountryRepository
import com.dalo.spring.model.Country
import spock.lang.Specification

class CountryServiceTest extends Specification {
    def BELARUS_COUNTRY = new Country(1L, "Belarus")
    def UNKNOWN_SOURCE_COUNTRY = new Country(2L, "Unknown source")

    def countryService

    def countryRepository = Mock(CountryRepository)

    def "getCountryByIP should return Belarus"() {
        given:
            countryRepository.findByName("Belarus") >> Optional.of(BELARUS_COUNTRY)
            countryService = new CountryServiceImpl(countryRepository)
        when:
            Country actual = countryService.getCountryByIP("5.100.193.158")
        then:
            actual == BELARUS_COUNTRY
    }

    def "getCountryBy Localhost IP should return unknown source"() {
        given:
            countryRepository.findByName(UNKNOWN_SOURCE_COUNTRY.getName()) >> Optional.of(UNKNOWN_SOURCE_COUNTRY)
            countryService = new CountryServiceImpl(countryRepository)
        when:
            Country actual = countryService.getCountryByIP("127.0.0.1")
        then:
            actual == UNKNOWN_SOURCE_COUNTRY
    }

    def "getCountryByIP should create country when it doesn't exist in database"() {
        given:
            def newCountry = new Country(id: 3L, name: "South Korea")
            countryRepository.findByName("South Korea") >> Optional.empty()
            countryRepository.save(new Country(id: null, name: "South Korea")) >> newCountry
            countryService = new CountryServiceImpl(countryRepository)
        when:
            Country actual = countryService.getCountryByIP("1.208.104.201")
        then:
            actual == newCountry
    }
}
