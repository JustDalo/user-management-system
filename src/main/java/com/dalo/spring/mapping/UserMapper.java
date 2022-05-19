package com.dalo.spring.mapping;

import com.dalo.spring.dto.UserDto;
import com.dalo.spring.model.User;
import com.dalo.spring.service.CryptService;
import com.dalo.spring.service.impl.Base64Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    private CryptService cryptService = new Base64Encryptor();

    public UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMiddleName(user.getMiddleName());
        dto.setSex(user.getSex());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setCountry(user.getCountry());
        dto.setImage(cryptService.cryptToString(user.getImage()));
        return dto;
    }

    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMiddleName(userDto.getMiddleName());
        user.setSex(userDto.getSex());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setCountry(userDto.getCountry());
        user.setImage(cryptService.cryptToByte(userDto.getImage()));
        return user;
    }
}
