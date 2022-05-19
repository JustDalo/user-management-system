package com.dalo.spring.service.impl

import com.dalo.spring.dao.UserRepository
import com.dalo.spring.dto.UserDto
import com.dalo.spring.exception.ResourceNotFoundException
import com.dalo.spring.mapping.UserMapper
import com.dalo.spring.model.Country
import com.dalo.spring.model.User
import spock.lang.Specification

class UserServiceTest extends Specification {
    def COUNTRY = new Country(id: 1L, name: "Belarus")

    User[] users = [
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

    UserDto[] usersDto = [
        new UserDto(
            id: 1L,
            firstName: "Danil",
            lastName: "Shyshla",
            middleName: "Valerevich",
            sex: "male",
            phoneNumber: "+375000000000",
            email: "@gmail.com",
            country: COUNTRY),
        new UserDto(
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

    def userMapper = new UserMapper();

    def userService = new UserServiceImpl(userRepository, userMapper)

    def "getAllUsers should return all users"() {
        given:
            1 * userRepository.findAll() >> users
        when:
            def resultListOfUsers = userService.getAllUsers()
        then:
            resultListOfUsers == usersDto.toList()
    }

    def "getUserById should return user with given id"() {
        given:
            def id = 1L
            1 * userRepository.findById(id) >> Optional.of(users[0])
        when:
            def resultUser = userService.getUserById(1L)
        then:
            resultUser == usersDto[0]
    }

    def "createUser should return created user"() {
        given:
            1 * userRepository.save(users[0]) >> users[0]
        when:
            UserDto returnedUser = userService.createUser(usersDto[0], COUNTRY, null)
        then:
            returnedUser == usersDto[0]
    }

    def "update user should return updated user"() {
        given:
            def id = 1L
            def newUser = new UserDto(
                id: 1L,
                firstName: "Danil",
                lastName: "Shyshla",
                middleName: "Valerevich",
                sex: "male",
                phoneNumber: "+375000000000",
                email: "@gmail.com",
                country: COUNTRY)
            1 * userRepository.findById(id) >> Optional.of(users[0])
        when:
            UserDto updatedUser = userService.updateUser(newUser, users[0].getId())
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
