package com.dalo.spring.controller;

import com.dalo.spring.dto.UserDtoFromClient;
import com.dalo.spring.dto.UserDtoToClient;
import com.dalo.spring.model.Country;
import com.dalo.spring.model.User;
import com.dalo.spring.service.CountryService;
import com.dalo.spring.service.IPRequestService;
import com.dalo.spring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<UserDtoToClient> getUserById(@PathVariable long id) {
        return new ResponseEntity<UserDtoToClient>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<User> getUserWithImageById(@PathVariable long id) {
        return new ResponseEntity<User>(userService.getUserWithImageById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDtoToClient>> getAllUsers() {
        return new ResponseEntity<List<UserDtoToClient>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<UserDtoFromClient> createUser(
        @RequestPart("properties") @Valid UserDtoFromClient user,
        @RequestPart(value = "file", required = false) MultipartFile file,
        HttpServletRequest httpServletRequest
    ) {
        Country country = countryService.getCountryByIP(requestService.getClientIP(httpServletRequest));
        return new ResponseEntity<UserDtoFromClient>(userService.createUser(user, country, file), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDtoFromClient> updateUser(
        @PathVariable long id,
        @Valid @RequestBody UserDtoFromClient user
    ) {
        return new ResponseEntity<UserDtoFromClient>(userService.updateUser(user, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
