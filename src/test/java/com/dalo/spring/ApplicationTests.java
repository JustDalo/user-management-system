package com.dalo.spring;

import com.dalo.spring.dao.UserRepository;
import com.dalo.spring.service.UserService;
import com.dalo.spring.service.impl.UserServiceImpl;
import com.maxmind.geoip2.model.CountryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class ApplicationTests {
}
