package com.dalo.spring.controller

import com.dalo.spring.dao.CountryRepository
import com.dalo.spring.dao.UserRepository
import com.dalo.spring.model.Country
import com.dalo.spring.model.User
import com.dalo.spring.service.CountryService
import com.dalo.spring.service.IPRequestService
import com.dalo.spring.service.UserService
import com.dalo.spring.supporter.LocalhostMySqlContainer
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.json.JacksonTester
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.MySQLContainer
import spock.lang.Shared
import org.springframework.http.HttpStatus;
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import javax.servlet.http.HttpServletRequest

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@WebMvcTest(controllers = [UserController])
class UserCRUDControllerTest extends Specification {
    @Autowired
    MockMvc mockMvc
    @Autowired
    UserService userService
    @Autowired
    CountryService countryService
    @Autowired
    IPRequestService ipRequestService

    private JacksonTester<User> jsonUsers;

    private static final String BASE_PATH = "/api/users"

    private static final Country COUNTRY =
            new Country(1L, "CountryName")

    def USERS = [
        new User(1L, "Daniil", "Shyshla", "Valerevich", "male", "+375333450040", "@mail.ru", COUNTRY),
        new User(2L, "Daniil", "Shyshlo", "Valerevich", "male", "+375333450040", "@mail.ru", COUNTRY),
        new User(3L, "Daniil", "Shyshli", "Valerevich", "male", "+375333450040", "@mail.ru", COUNTRY)
    ]

    def setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }
    
    def "UserController should return status 200 when getting user"() {
        given:
            userService.getUserById(1L) >> USERS[0]
        when:
            def response = mockMvc.perform(get(BASE_PATH + "/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
        then:
            response.getStatus() == HttpStatus.OK.value()
    }

    def "UserController should return status 200 when getting all users"() {
        given:
            userService.getAllUsers() >> USERS.toList()
        when:
        def response = mockMvc.perform(get(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.OK.value()
            jsonUsers.write(USERS[0]).getJson() == response.getContentAsString()
    }

    def "UserController should return status CREATED when creating new user"() {
        given:
            def request = Mock(HttpServletRequest)
            ipRequestService.getClientIP(request) >> "ip"
            countryService.getCountryByIP("ip") >> COUNTRY
            userService.createUser(USERS[0], COUNTRY) >> USERS[0]
        when:
        def response = mockMvc.perform(post(BASE_PATH).
                contentType(MediaType.APPLICATION_JSON).content(jsonUsers.write(USERS[0]).getJson()))
                .andReturn()
                .getResponse();
        then:
            response.getStatus() == HttpStatus.CREATED.value()
    }

    @TestConfiguration
    static class Config {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()
        @Bean
        UserService userService() {
            return detachedMockFactory.Mock(UserService)
        }
        @Bean
        CountryService countryService() {
            return detachedMockFactory.Mock(CountryService)
        }
        @Bean
        IPRequestService ipRequestService() {
            return detachedMockFactory.Mock(IPRequestService)
        }
    }
}
