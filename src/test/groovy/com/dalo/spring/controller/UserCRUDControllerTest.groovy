package com.dalo.spring.controller

import com.dalo.spring.dao.CountryRepository
import com.dalo.spring.dao.UserRepository
import com.dalo.spring.model.Country
import com.dalo.spring.model.User
import com.dalo.spring.service.CountryService
import com.dalo.spring.service.IPRequestService
import com.dalo.spring.service.UserService
import com.dalo.spring.utils.LocalhostMySqlContainer
import com.dalo.spring.utils.TestPropertiesInitializer
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.HttpStatus
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = [TestPropertiesInitializer])
@AutoConfigureMockMvc
class UserCRUDControllerTest extends Specification {
    @Shared
    MySQLContainer mySqlContainer = LocalhostMySqlContainer.getInstance()
    @Autowired
    MockMvc mockMvc
    @Autowired
    UserService userService
    @Autowired
    CountryService countryService
    @Autowired
    IPRequestService ipRequestService
    @Autowired
    UserRepository userRepository
    @Autowired
    CountryRepository countryRepository

    private JacksonTester<User> jsonUsers

    private static final String BASE_PATH = "/api/users"

    def setup() {
        JacksonTester.initFields(this, new ObjectMapper())
    }

    def cleanup() {
        userRepository.deleteAll()
        countryRepository.deleteAll()
    }

    def "UserController findById should return status 200 OK when getting user"() {
        given:
            User savedUser = createNewUser()
        when:
            def response = mockMvc.perform(get(BASE_PATH + "/${savedUser.getId()}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.OK.value()
    }

    def "UserController findById should return status 404 NOT FOUND when getting user that does not exist in database"() {
        when:
            def response = mockMvc.perform(get(BASE_PATH + "/0")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
        then:
            response.getStatus() == HttpStatus.NOT_FOUND.value()
    }

    def "UserController findAll should return status 200 OK when getting all users"() {
        given:
            Country belarusCountry = countryRepository.save(new Country(name: "Belarus"))
            userRepository.save(new User(
                    firstName: "Danil",
                    lastName: "Shyshla",
                    middleName: "Valerevich",
                    sex: "male",
                    phoneNumber: "+375111111111",
                    email: "@gmail.com",
                    country: belarusCountry
            ))
            userRepository.save(new User(
                    firstName: "Ivan",
                    lastName: "Ivanov",
                    middleName: "Ivanovich",
                    sex: "male",
                    phoneNumber: "+375000000000",
                    email: "@gmail.com",
                    country: belarusCountry
            ))
        when:
            def response = mockMvc.perform(get(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.OK.value()
    }

    def "UserController createUser should return status 201 CREATED when creating new user"() {
        given:
            Country belarusCountry = countryRepository.save(new Country(name: "Belarus"))
            def request = Mock(HttpServletRequest)
            ipRequestService.getClientIP(request) >> null
        when:
        def response = mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUsers.write(new User(
                        firstName: "Ivan",
                        lastName: "Ivanov",
                        middleName: "Ivanovich",
                        sex: "male",
                        phoneNumber: "+375000000000",
                        email: "@gmail.com",
                        country: belarusCountry
                )).getJson()))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.CREATED.value()
    }

    def "UserController updateUser should return status 200 OK when user is updated"() {
        given:
            Country belarusCountry = countryRepository.save(new Country(name: "Belarus"))
            def savedUser = userRepository.save(new User(
                firstName: "Danil",
                lastName: "Shyshla",
                middleName: "Valerevich",
                sex: "male",
                phoneNumber: "+375111111111",
                email: "@gmail.com",
                country: belarusCountry
            ))
        when:
            def response = mockMvc.perform(put(BASE_PATH + "/${savedUser.getId()}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUsers.write(new User(
                            id: savedUser.getId(),
                            firstName: "Daniil",
                            lastName: "Shyshla",
                            sex: "male",
                            phoneNumber: "+375222222222",
                            email: "@gmail.com",
                            country: belarusCountry
                    )).getJson()))
                    .andReturn()
                    .getResponse()
        then:
        response.getStatus() == HttpStatus.OK.value()
    }

    def "UserController deleteUser should return status 200 OK when user is deleted"() {
        given:
            User savedUser = createNewUser()
        when:
            def response = mockMvc.perform(delete(BASE_PATH + "/${savedUser.getId()}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.OK.value()
    }

    def "UserController deleteUser should return status 404 NOT FOUND when used with given id is not found"() {
        when:
            def response = mockMvc.perform(delete(BASE_PATH + "/1"))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.NOT_FOUND.value()
    }

    private def createNewUser() {
        Country belarusCountry = countryRepository.save(new Country(name: "Belarus"))
        return userRepository.save(new User(
            firstName: "Danil",
            lastName: "Shyshla",
            middleName: "Valerevich",
            sex: "male",
            phoneNumber: "+375111111111",
            email: "@gmail.com",
            country: belarusCountry
        ))
    }
}
