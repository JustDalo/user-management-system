package com.dalo.spring.dto;

import com.dalo.spring.model.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String middleName;
    @NotBlank
    private String sex;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String email;
    private String image;
    private Country country;
}
