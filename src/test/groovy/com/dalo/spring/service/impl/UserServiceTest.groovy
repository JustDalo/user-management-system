package com.dalo.spring.service.impl

import com.dalo.spring.dao.UserRepository
import com.dalo.spring.exception.ResourceNotFoundException
import com.dalo.spring.model.Country
import com.dalo.spring.model.User
import spock.lang.Specification

class UserServiceTest extends Specification {
    def COUNTRY = new Country(id: 1L, name: "Belarus")

    def users = [
        new User(
            id: 1L,
            firstName: "Danil",
            lastName: "Shyshla",
            middleName: "Valerevich",
            sex: "male",
            phoneNumber: "+375000000000",
            email: "@gmail.com",
            country: COUNTRY),
        new User(
            id: 2L,
            firstName: "Daniil",
            lastName: "Shyshlo",
            middleName: "Valer'evich",
            sex: "male",
            phoneNumber: "+375111111111",
            email: "@gmail.com",
            country: COUNTRY)
    ]

    def userRepository = Mock(UserRepository)

    def userService = new UserServiceImpl(userRepository)

    def "getAllUsers should return all users"() {
        given:
            1 * userRepository.findAll() >> users
        when:
            def resultListOfUsers = userService.getAllUsers()
        then:
            resultListOfUsers == users
    }

    def "getUserById should return user with given id"() {
        given:
            def id = 1L
            1 * userRepository.findById(id) >> Optional.of(users[0])
        when:
            def resultUser = userService.getUserById(1L)
        then:
            resultUser == users[0]
    }

    def "createUser should return created user"() {
        given:
            1 * userRepository.save(users[0]) >> users[0]
        when:
            User returnedUser = userService.createUser(users[0], COUNTRY)
        then:
            returnedUser == users[0]
    }

    def "update user should return updated user"() {
        given:
            def id = 1L
            def newUser = new User(
                id: 1L,
                firstName: "Danil",
                lastName: "Shyshla",
                middleName: "Valerevich",
                sex: "male",
                phoneNumber: "+375000000000",
                email: "@gmail.com",
                country: COUNTRY)
            1  * userRepository.findById(id) >> Optional.of(users[0])
        when:
            User updatedUser = userService.updateUser(newUser, users[0].getId())
        then:
            updatedUser == newUser
    }

    def "get userById should return ResourceNotFoundException"() {
        given:
            def id = 1000L
            1 * userRepository.findById(id) >> Optional.empty()
        when:
            userService.getUserById(id)
        then:
            thrown(ResourceNotFoundException)
    }
}
