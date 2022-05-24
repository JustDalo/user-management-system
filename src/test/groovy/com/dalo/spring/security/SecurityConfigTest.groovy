package com.dalo.spring.security

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
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = [TestPropertiesInitializer])
@AutoConfigureMockMvc
class SecurityConfigTest extends Specification {
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

    @WithMockUser(authorities = "ADMIN")
    def "findById should return status 200 OK for ADMIN"() {
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

    @WithMockUser(authorities = "USER")
    def "findById should return status 403 FORBIDDEN for USER"() {
        given:
            User savedUser = createNewUser()
        when:
            def response = mockMvc.perform(get(BASE_PATH + "/${savedUser.getId()}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.FORBIDDEN.value()
    }

    @WithMockUser(authorities = "ADMIN")
    def "findAll should return status 200 OK for ADMIN"() {
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

    @WithMockUser(authorities = "USER")
    def "findAll should return status 403 FORBIDDEN when getting all users"() {
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
            response.getStatus() == HttpStatus.FORBIDDEN.value()
    }

    @WithMockUser(authorities = "ADMIN")
    def "createUser should return status 201 CREATED for ADMIN"() {
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

    @WithMockUser(authorities = "USER")
    def "createUser should return status 201 CREATED for USER"() {
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

    @WithMockUser(authorities = "ADMIN")
    def "updateUser should return status 200 OK for ADMIN"() {
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

    @WithMockUser(authorities = "USER")
    def "updateUser should return status 200 OK for USER"() {
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

    @WithMockUser(authorities = "ADMIN")
    def "deleteUser should return status 200 OK for ADMIN"() {
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

    @WithMockUser(authorities = "USER")
    def "deleteUser should return status 403 FORBIDDEN for USER"() {
        given:
            User savedUser = createNewUser()
        when:
            def response = mockMvc.perform(delete(BASE_PATH + "/${savedUser.getId()}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.FORBIDDEN.value()
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
