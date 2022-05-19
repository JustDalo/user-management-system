package com.dalo.spring.mapping;

import com.dalo.spring.dto.UserDto;
import com.dalo.spring.model.User;
import com.dalo.spring.service.FileUploadService;
import com.dalo.spring.service.impl.FileUploadServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    private final FileUploadService fileUploadService = new FileUploadServiceImpl();

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
        if (user.getImage() != null) {
            dto.setImage(fileUploadService.sentFile(user.getImage()));
        }
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
        if (userDto.getImage() != null) {
            user.setImage(fileUploadService.uploadFile(userDto.getImage()));
        }
        return user;
    }
}
