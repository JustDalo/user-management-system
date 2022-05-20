package com.dalo.spring.service.impl;

import com.dalo.spring.annotation.Loggable;
import com.dalo.spring.annotation.Metric;
import com.dalo.spring.dao.UserRepository;
import com.dalo.spring.dto.UserDto;
import com.dalo.spring.exception.ResourceNotFoundException;
import com.dalo.spring.mapping.UserMapper;
import com.dalo.spring.model.Country;
import com.dalo.spring.model.User;
import com.dalo.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Loggable
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto user, Country country) {
        user.setCountry(country);
        return userMapper.mapToUserDto(userRepository.save(userMapper.mapToUser(user)));
    }

    @Override
    public UserDto updateUser(UserDto user, Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "Id", id));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setMiddleName(user.getMiddleName());
        existingUser.setSex(user.getSex());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setEmail(user.getEmail());

        userRepository.save(existingUser);

        return userMapper.mapToUserDto(existingUser);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return userMapper.mapToUserDto(user.get());
        }
        throw new ResourceNotFoundException("User", "Id", id);
    }

    @Override
    @Metric("getAllUsers")
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::mapToUserDto).collect(Collectors.toList());
    }
}
