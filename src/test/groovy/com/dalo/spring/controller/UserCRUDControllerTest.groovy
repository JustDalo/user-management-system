package com.dalo.spring.controller

import com.dalo.spring.dao.UserRepository
import com.dalo.spring.model.User
import com.dalo.spring.supporter.LocalhostMySqlContainer
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.MySQLContainer
import spock.lang.Shared
import spock.lang.Specification

import java.sql.ResultSet
import java.sql.Statement


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@org.testcontainers.spock.Testcontainers
class UserCRUDControllerTest extends Specification {
    @Shared
    MySQLContainer mySqlContainer = LocalhostMySqlContainer.getInstance()
    @Autowired
    UserRepository userRepository

    def "creating statement"() {
        given:
            HikariConfig hikariConfig = new HikariConfig()
            hikariConfig.setJdbcUrl(mySqlContainer.jdbcUrl)
            hikariConfig.setUsername("root")
            hikariConfig.setPassword("root")
            HikariDataSource dataSource = new HikariDataSource(hikariConfig)
        when: "querying the database"
            Statement statement = dataSource.getConnection().createStatement()
            statement.execute("SELECT 1")
            ResultSet resultSet = statement.getResultSet()
            resultSet.next()
        then: "result is returned"
            int resultSetInt = resultSet.getInt(1)
            resultSetInt == 1
    }

    def "user repository get all users"() {
        when:
            List<User> users = userRepository.findAll()
        then:
           users.size() == 0
    }
}
