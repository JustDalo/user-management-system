package com.dalo.spring.service;

import com.dalo.spring.annotation.Metric;
import com.dalo.spring.dto.UserDtoFromClient;
import com.dalo.spring.dto.UserDtoToClient;
import com.dalo.spring.model.Country;
import com.dalo.spring.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserDtoToClient getUserById(Long id);

    User getUserWithImageById(Long id);

    List<UserDtoToClient> getAllUsers();

    UserDtoFromClient createUser(UserDtoFromClient user, Country country, MultipartFile file);

    UserDtoFromClient updateUser(UserDtoFromClient user, Long id);

    void deleteUserById(Long id);
}
