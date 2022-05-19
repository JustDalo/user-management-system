package com.dalo.spring.dto;

import com.dalo.spring.model.Country;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    public Long id;
    @NotBlank
    public String firstName;
    @NotBlank
    public String lastName;
    public String middleName;
    @NotBlank
    public String sex;
    @NotBlank
    public String phoneNumber;
    @NotBlank
    public String email;
    public MultipartFile image;
    public Country country;
}
