package com.dalo.spring.dto;

import com.dalo.spring.model.Country;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    private Long id;
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
    public String image;
    public Country country;

    public UserDto() {
    }
}
