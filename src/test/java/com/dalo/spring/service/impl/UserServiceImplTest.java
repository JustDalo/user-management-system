package com.dalo.spring.service.impl;

import com.dalo.spring.dao.UserRepository;
import com.dalo.spring.exception.ResourceNotFoundException;
import com.dalo.spring.model.Country;
import com.dalo.spring.model.User;
import com.dalo.spring.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith({MockitoExtension.class})
class UserServiceImplTest {
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    private static final Country COUNTRY =
            new Country(1L, "CountryName");

    private static final User[] USERS = {
            new User(1L, "Daniil", "Shyshla", "Valerevich", "male", "+375333450040", "@mail.ru", COUNTRY),
            new User(2L, "Daniil", "Shyshlo", "Valerevich", "male", "+375333450040", "@mail.ru", COUNTRY),
            new User(3L, "Daniil", "Shyshli", "Valerevich", "male", "+375333450040", "@mail.ru", COUNTRY)
    };

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void createUserShouldReturnCreatedUserTest() {
        User user = USERS[0];

        Mockito.when(userRepository.save(user)).thenReturn(USERS[0]);
        User actual = userService.createUser(user, COUNTRY);

        assertEquals(user, actual);
    }

    @Test
    void updateUser() {
        User user = USERS[0];
        User newUser = new User(1L, "Daniil", "Shyshla", "Valerevich", "male", "+375333450040", "@mail.ru", COUNTRY);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User actual = userService.updateUser(newUser, user.getId());
        assertEquals(newUser, actual);
    }

    @Test
    void deleteUseById() {
        User user = USERS[0];

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUserById(user.getId());
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(user.getId());
    }

    @Test
    void getUserByIdShouldReturnUserWithSuchIdTest() {
        User user = USERS[0];

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(USERS[0]));
        User actual = userService.getUserById(1L);

        assertEquals(user, actual);
    }

    @Test
    void getUserByIdShouldThrowResourceNotFoundException() {
        Mockito.when(userRepository.findById(1000L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1000L));
    }

    @Test
    void getAllUsersShouldReturnAllUsersTest() {
        List<User> users = Arrays.asList(USERS);

        Mockito.when(userRepository.findAll()).thenReturn(Arrays.stream(USERS).toList());
        List<User> actual = userService.getAllUsers();

        assertEquals(users, actual);
    }
}