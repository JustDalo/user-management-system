package com.dalo.spring.mapping;

import com.dalo.spring.dto.UserDto;
import com.dalo.spring.model.User;
import com.dalo.spring.service.EncoderService;
import com.dalo.spring.service.impl.Base64Encoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    private EncoderService encoderService = new Base64Encoder();

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .middleName(user.getMiddleName())
            .sex(user.getSex())
            .phoneNumber(user.getPhoneNumber())
            .email(user.getEmail())
            .country(user.getCountry())
            .image(encoderService.encodeToString(user.getImage()))
            .build();
    }

    public User mapToUser(UserDto userDto) {
        return User.builder()
            .id(userDto.getId())
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .middleName(userDto.getMiddleName())
            .sex(userDto.getSex())
            .phoneNumber(userDto.getPhoneNumber())
            .email(userDto.getEmail())
            .country(userDto.getCountry())
            .image(encoderService.encodeToByte(userDto.getImage()))
            .build();
    }
}
