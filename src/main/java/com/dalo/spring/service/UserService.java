package com.dalo.spring.service;

import com.dalo.spring.annotation.Metric;
import com.dalo.spring.dto.UserDto;
import com.dalo.spring.model.Country;
import com.dalo.spring.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {
    UserDto getUserById(Long id);
    @Metric("getAllUsers")
    List<UserDto> getAllUsers();
    UserDto createUser(UserDto user, Country country);
    UserDto updateUser(UserDto user, Long id);
    void deleteUserById(Long id);
}
