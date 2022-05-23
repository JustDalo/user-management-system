package com.dalo.spring.service.impl;

import com.dalo.spring.annotation.Loggable;
import com.dalo.spring.annotation.Metric;
import com.dalo.spring.dao.UserRepository;
import com.dalo.spring.dto.UserDtoFromClient;
import com.dalo.spring.dto.UserDtoToClient;
import com.dalo.spring.exception.ResourceNotFoundException;
import com.dalo.spring.mapping.FromUserMapper;
import com.dalo.spring.mapping.ToUserMapper;
import com.dalo.spring.model.Country;
import com.dalo.spring.model.User;
import com.dalo.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FromUserMapper fromUserMapper;
    private final ToUserMapper toUserMapper;

    @Override
    public UserDtoFromClient createUser(UserDtoFromClient user, Country country, MultipartFile file) {
        user.setCountry(country);
        if (file != null) {
            try {
                user.setImage(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            user.setImage(null);
        }
        return fromUserMapper.mapToUserDto(userRepository.save(fromUserMapper.mapToUser(user)));
    }

    @Override
    public UserDtoFromClient updateUser(UserDtoFromClient user, Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "Id", id));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setMiddleName(user.getMiddleName());
        existingUser.setSex(user.getSex());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setEmail(user.email);

        userRepository.save(existingUser);

        return fromUserMapper.mapToUserDto(existingUser);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        userRepository.deleteById(id);
    }

    @Override
    public UserDtoToClient getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return toUserMapper.mapToUserDto(user.get());
        }
        throw new ResourceNotFoundException("User", "Id", id);
    }

    @Override
    public User getUserWithImageById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ResourceNotFoundException("User", "Id", id);
    }

    @Override
    public List<UserDtoToClient> getAllUsers() {
        return userRepository.findAll().stream().map(toUserMapper::mapToUserDto).collect(Collectors.toList());
    }
}
