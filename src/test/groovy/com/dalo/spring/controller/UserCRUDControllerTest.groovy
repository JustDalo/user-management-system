package com.dalo.spring.controller

import com.dalo.spring.dao.CountryRepository
import com.dalo.spring.dao.UserRepository
import com.dalo.spring.model.Country
import com.dalo.spring.model.User
import com.dalo.spring.supporter.LocalhostMySqlContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.MySQLContainer
import spock.lang.Shared
import spock.lang.Specification


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@org.testcontainers.spock.Testcontainers
class UserCRUDControllerTest extends Specification {
    @Shared
    MySQLContainer mySqlContainer = LocalhostMySqlContainer.getInstance()
    @Autowired
    UserRepository userRepository
    @Autowired
    CountryRepository countryRepository

    def "save new user to db"() {
        given:
            def COUNTRY = new Country(id: 1L, name: "Belarus")
            def existedUser = new User(id: 1L, firstName: "Danil", lastName: "Shyshla", middleName: "Valerevich", sex: "male", phoneNumber: "+375333450040", email: "@mail.ru", country: COUNTRY)
            def userToSave = new User(firstName: "Danil", lastName: "Shyshla", middleName: "Valerevich", sex: "male", phoneNumber: "+375333450040", email: "@mail.ru", country: COUNTRY)
            countryRepository.save(COUNTRY)
        when:
            def savedUser = userRepository.save(userToSave)
        then:
            savedUser == existedUser
    }

    def "get all users should return all created users"() {
        when:
            List<User> users = userRepository.findAll()
        then:
           users.size() == 1
    }

    def "find user by id should return user with given id"() {
        given:
            def COUNTRY = new Country(id: 1L, name: "Belarus")
            def savedUser = new User(id: 1L, firstName: "Danil", lastName: "Shyshla", middleName: "Valerevich", sex: "male", phoneNumber: "+375333450040", email: "@mail.ru", country: COUNTRY)
        when:
            def user = userRepository.findById(1L)
        then:
            user == Optional.of(savedUser)
    }
}
