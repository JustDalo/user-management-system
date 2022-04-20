package com.dalo.spring.controller;

import com.dalo.spring.controller.UserController;
import com.dalo.spring.model.Country;
import com.dalo.spring.model.User;
import com.dalo.spring.service.CountryService;
import com.dalo.spring.service.IPRequestService;
import com.dalo.spring.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties"
)
class UserControllerTest {
    private static final String BASE_PATH = "/api/users";

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private CountryService countryService;
    @Mock
    private IPRequestService ipRequestService;
    @InjectMocks
    private UserController userController;

    private JacksonTester<User> jsonUsers;

    private static final Country COUNTRY =
            new Country(1L, "CountryName");

    private static final User[] USERS = {
            new User(1L, "Daniil", "Shyshla", "Valerevich", "male", "+375333450040", "@mail.ru", COUNTRY),
            new User(2L, "Daniil", "Shyshlo", "Valerevich", "male", "+375333450040", "@mail.ru", COUNTRY),
            new User(3L, "Daniil", "Shyshli", "Valerevich", "male", "+375333450040", "@mail.ru", COUNTRY)
    };


    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    void getUserByIdShouldReturnHttpStatusOK() throws Exception {
        Mockito.when(userService.getUserById(1L)).thenReturn(USERS[0]);

        MockHttpServletResponse response = mockMvc.perform(get(BASE_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jsonUsers.write(USERS[0]).getJson(), response.getContentAsString());
    }

    @Test
    void getAllUsersShouldReturnHttpStatusOK() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(USERS));

        MockHttpServletResponse response = mockMvc.perform(get(BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void createUserShouldReturnHttpStatusCREATED() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(ipRequestService.getClientIP(request)).thenReturn("ip");
        Mockito.when(countryService.getCountryByIP("ip")).thenReturn(COUNTRY);
        Mockito.when(userService.createUser(USERS[0], COUNTRY)).thenReturn(USERS[0]);

        MockHttpServletResponse response = mockMvc.perform(post(BASE_PATH).
                        contentType(MediaType.APPLICATION_JSON).content(jsonUsers.write(USERS[0]).getJson()))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void updateUserShouldReturnHttpStatusOK() throws Exception {
        Mockito.when(userService.getUserById(1L)).thenReturn(USERS[0]);

        MockHttpServletResponse response = mockMvc.perform(put(BASE_PATH + "/1").contentType(MediaType.APPLICATION_JSON)
                .content(jsonUsers.write(USERS[0]).getJson())).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteUserByIdShouldReturnHttpStatusOK() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(delete(BASE_PATH + "/1")).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}