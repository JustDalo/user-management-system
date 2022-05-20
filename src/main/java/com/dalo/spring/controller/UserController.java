package com.dalo.spring.controller;

import com.dalo.spring.dto.UserDto;
import com.dalo.spring.model.Country;
import com.dalo.spring.service.CountryService;
import com.dalo.spring.service.IPRequestService;
import com.dalo.spring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final CountryService countryService;
    private final IPRequestService requestService;

    public UserController(
        UserService userService,
        CountryService countryService,
        IPRequestService requestService
    ) {
        this.userService = userService;
        this.countryService = countryService;
        this.requestService = requestService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        return new ResponseEntity<UserDto>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<List<UserDto>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
        @Valid @ModelAttribute UserDto user,
        HttpServletRequest httpServletRequest
    ) {
        Country country = countryService.getCountryByIP(requestService.getClientIP(httpServletRequest));
        return new ResponseEntity<UserDto>(userService.createUser(user, country), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long id, @Valid @RequestBody UserDto user) {
        return new ResponseEntity<UserDto>(userService.updateUser(user, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
