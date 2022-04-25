package com.dalo.spring.utils

import com.dalo.spring.service.IPRequestService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import spock.mock.DetachedMockFactory

@Configuration
class MockConfiguration {
    private DetachedMockFactory mockFactory = new DetachedMockFactory()

    @Bean
    @Primary
    IPRequestService mockIpRequestService() {
        return mockFactory.Mock(IPRequestService)
    }
}
