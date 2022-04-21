package com.dalo.spring.service.impl

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class IpRequestServiceTest extends Specification {
    def IPRequestService

    def "getClientIP with X-Forwarded-For header should return client IP"() {
        given:
            def mockedRequest = Mock(HttpServletRequest)
            mockedRequest.getHeader("X-Forwarded-For") >> "129.78.138.66, 129.78.64.103"
            IPRequestService = new IPRequestServiceImpl()
        when:
            String resultIp = IPRequestService.getClientIP(mockedRequest)
        then:
            resultIp == "129.78.138.66"
    }

    def "getClientIP with Proxy-Client-IP header should return client IP"() {
        given:
            def mockedRequest = Mock(HttpServletRequest)
            mockedRequest.getHeader("X-Forwarded-For") >> null
            mockedRequest.getHeader("Proxy-Client-IP") >> "129.78.138.67, 129.78.64.103"
            IPRequestService = new IPRequestServiceImpl()
        when:
            String resultIp = IPRequestService.getClientIP(mockedRequest)
        then:
            resultIp == "129.78.138.67"
    }

    def "getClientIP with WL-Proxy-Client-IP header should return client IP"() {
        given:
            def mockedRequest = Mock(HttpServletRequest)
            mockedRequest.getHeader("X-Forwarded-For") >> null
            mockedRequest.getHeader("Proxy-Client-IP") >> null
            mockedRequest.getHeader("WL-Proxy-Client-IP") >> "129.78.138.68, 129.78.64.103"
            IPRequestService = new IPRequestServiceImpl()
        when:
            String resultIp = IPRequestService.getClientIP(mockedRequest)
        then:
            resultIp == "129.78.138.68"
    }

    def "getClientIP from localhost should return localhost IP"() {
        given:
            def mockedRequest = Mock(HttpServletRequest)
            mockedRequest.getRemoteAddr() >> "0:0:0:0:0:0:0:1"
            IPRequestService = new IPRequestServiceImpl()
        when:
            String resultIp = IPRequestService.getClientIP(mockedRequest)
        then:
            resultIp == "127.0.0.1"
    }
}


