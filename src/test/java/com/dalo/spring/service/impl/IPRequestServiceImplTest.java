package com.dalo.spring.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class IPRequestServiceImplTest {
    private IPRequestServiceImpl ipRequestService;

    @BeforeEach
    public void setUp() {
        ipRequestService = new IPRequestServiceImpl();
    }

    @Test
    void getClientIPWithXForwardedForHeaderShouldReturnClientIPTest() {
        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

        Mockito.when(mockedRequest.getHeader("X-Forwarded-For")).thenReturn("129.78.138.66, 129.78.64.103");
        String actual = ipRequestService.getClientIP(mockedRequest);

        assertEquals("129.78.138.66", actual);
    }

    @Test
    void getClientIPWithProxyClientIPHeaderShouldReturnClientIPTest() {
        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

        Mockito.when(mockedRequest.getHeader("X-Forwarded-For")).thenReturn(null);
        Mockito.when(mockedRequest.getHeader("Proxy-Client-IP")).thenReturn("129.78.138.67, 129.78.64.103");
        String actual = ipRequestService.getClientIP(mockedRequest);

        assertEquals("129.78.138.67", actual);
    }

    @Test
    void getClientIPWithWLProxyClientIPHeaderShouldReturnClientIPTest() {
        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

        Mockito.when(mockedRequest.getHeader("X-Forwarded-For")).thenReturn(null);
        Mockito.when(mockedRequest.getHeader("Proxy-Client-IP")).thenReturn(null);
        Mockito.when(mockedRequest.getHeader("WL-Proxy-Client-IP")).thenReturn("129.78.138.68, 129.78.64.103");
        String actual = ipRequestService.getClientIP(mockedRequest);

        assertEquals("129.78.138.68", actual);
    }

    @Test
    void getClientIPFromLocalHostShouldReturnLocalhostIPTest() {
        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

        Mockito.when(mockedRequest.getRemoteAddr()).thenReturn("0:0:0:0:0:0:0:1");
        String actual = ipRequestService.getClientIP(mockedRequest);

        assertEquals("127.0.0.1", actual);
    }


}