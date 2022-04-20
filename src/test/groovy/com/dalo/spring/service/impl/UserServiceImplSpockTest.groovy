package com.dalo.spring.service.impl

import com.dalo.spring.dao.UserRepository
import com.dalo.spring.model.Country
import com.dalo.spring.model.User
import spock.lang.Specification

class UserServiceImplSpockTest extends Specification {
    def COUNTRY = new Country(id: 1L, name: "Belarus")

    def users = [
            new User(id: 1L, firstName: "Daniil", lastName: "Shyshla", middleName: "Valerevich", sex: "male", phoneNumber: "+375333450040", email: "@mail.ru", country: COUNTRY),


    ]

    def "create user should return created user"() {
        given:
        def userDao = Mock(UserRepository)
        def userService = new UserServiceImpl(userDao);
        when:
        userService.getAllUsers()
        then:
        1 * userDao.findAll() >> users
    }
}
