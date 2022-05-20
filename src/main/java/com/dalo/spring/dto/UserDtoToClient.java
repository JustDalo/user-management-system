package com.dalo.spring.dto;

import com.dalo.spring.model.Country;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.net.URL;

@Builder
@Getter
public class UserDtoToClient {
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
    private URL image;
    private Country country;
}
