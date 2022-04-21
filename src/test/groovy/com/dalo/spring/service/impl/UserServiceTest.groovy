package com.dalo.spring.service.impl

import com.dalo.spring.dao.UserRepository
import com.dalo.spring.exception.ResourceNotFoundException
import com.dalo.spring.model.Country
import com.dalo.spring.model.User
import spock.lang.Specification

class UserServiceTest extends Specification {
    def COUNTRY = new Country(id: 1L, name: "Belarus")

    def users = [
            new User(id: 1L, firstName: "Daniil", lastName: "Shyshla", middleName: "Valerevich", sex: "male", phoneNumber: "+375333450040", email: "@mail.ru", country: COUNTRY),
            new User(id: 2L, firstName: "Daniil", lastName: "Shyshla", middleName: "Valerevich", sex: "male", phoneNumber: "+375333450040", email: "@mail.ru", country: COUNTRY)
    ]

    def userService

    def "getAllUsers should return all users user"() {
        given:
            def userRepository = Stub(UserRepository)
            userRepository.findAll() >> users
            userService = new UserServiceImpl(userRepository)
        when:
            def resultListOfUsers = userService.getAllUsers()
        then:
            resultListOfUsers == users
    }

    def "createUser should return created user"() {
        given:
            def userRepository = Stub(UserRepository)
            userRepository.save(users[0]) >> users[0]
            userService = new UserServiceImpl(userRepository)
        when:
            User returnedUser = userService.createUser(users[0], COUNTRY)
        then:
            returnedUser == users[0]
    }

    def "Update user should return updated user"() {
        given:
            def id = 1L
            def newUser = new User(id: 1L, firstName: "Danil", lastName: "Shyshla", middleName: "Valerevich", sex: "male", phoneNumber: "+375333450040", email: "@mail.ru", country: COUNTRY)
            def userRepository = Stub(UserRepository)
            userRepository.findById(id) >> Optional.of(users[0])
            userService = new UserServiceImpl(userRepository)
        when:
            User updatedUser = userService.updateUser(newUser, users[0].getId())
        then:
            updatedUser == newUser
    }

    def "get userById should return ResourceNotFoundException"() {
        given:
            def id = 1000L
            def userRepository = Stub(UserRepository)
            userRepository.findById(id) >> Optional.empty()
            userService = new UserServiceImpl(userRepository)
        when:
            userService.getUserById(id)
        then:
            thrown(ResourceNotFoundException)
    }

    def "deleteUser should call for delete one time"() {
        given:
            def id = 1L
            def userRepository = Mock(UserRepository)
            userRepository.findById(id) >> Optional.of(users[0])
            userService = new UserServiceImpl(userRepository)
        when:
            userService.deleteUserById(id)
        then:
            1 * userRepository.deleteById(id);
    }
}
