package com.dalo.spring.mapping;

import com.dalo.spring.dto.UserDtoFromClient;
import com.dalo.spring.model.User;
import org.springframework.stereotype.Service;

@Service
public class FromUserMapper {
    public UserDtoFromClient mapToUserDto(User user) {
        return UserDtoFromClient.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .middleName(user.getMiddleName())
            .sex(user.getSex())
            .phoneNumber(user.getPhoneNumber())
            .email(user.getEmail())
            .country(user.getCountry())
            .image(user.getImage())
            .build();
    }

    public User mapToUser(UserDtoFromClient userDto) {
        return User.builder()
            .id(userDto.getId())
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .middleName(userDto.getMiddleName())
            .sex(userDto.getSex())
            .phoneNumber(userDto.getPhoneNumber())
            .email(userDto.getEmail())
            .country(userDto.getCountry())
            .image(userDto.getImage())
            .build();
    }
}
