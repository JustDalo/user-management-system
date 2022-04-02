package com.dalo.spring.service;

import com.dalo.spring.model.Country;
import com.dalo.spring.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getUserById(Long id);
    List<User> getAllUsers();
    User createUser(User user, Country country);
    User updateUser(User user, Long id);
    void deleteUserById(Long id);
}