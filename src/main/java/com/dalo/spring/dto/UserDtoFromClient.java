package com.dalo.spring.dto;

import com.dalo.spring.model.Country;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDtoFromClient {
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
    public byte[] image;
    public Country country;
}
