package com.dalo.spring.mapping;

import com.dalo.spring.dto.UserDtoToClient;
import com.dalo.spring.model.User;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Service
public class ToUserMapper {
    public UserDtoToClient mapToUserDto(User user) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("localhost").setPath("api/users/" + user.getId() + "/image");
        try {
            return UserDtoToClient.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .sex(user.getSex())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .country(user.getCountry())
                .image(builder.build().toURL())
                .build();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
