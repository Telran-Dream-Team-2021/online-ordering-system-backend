package telran.oos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import telran.oos.api.dto.AuthRequestDto;
import telran.oos.api.dto.AuthResponseDto;
import telran.oos.api.dto.UserDto;
import telran.oos.service.UserService;
import telran.oos.service.implementation.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static telran.oos.api.ApiConstants.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;

    private static String token;
    private static UserDto userDto;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @Order(0)
    void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    @Order(1)
    void login() throws Exception {
        AuthRequestDto authRequestDto = new AuthRequestDto("admin@tel-ran.co.il", "admin1234", null);

        String resJson = mockMvc.perform(
                MockMvcRequestBuilders.post(LOGIN_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(mapper.writeValueAsString(authRequestDto))
        ).andReturn().getResponse().getContentAsString();

        AuthResponseDto authResponseDto = mapper.readValue(resJson, AuthResponseDto.class);

        assertNotNull(authResponseDto.getAccessToken());
        assertNotEquals("", authResponseDto.getAccessToken());

        token = authResponseDto.getAccessToken();
    }

    @Test
    @Order(2)
    void register() throws Exception {
        String email = "test@tel-ran.co.il";
        AuthRequestDto authRequestDto = new AuthRequestDto(email, "test1234", null);

        String resJson = mockMvc.perform(
                MockMvcRequestBuilders.post(LOGIN_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(mapper.writeValueAsString(authRequestDto))
        ).andReturn().getResponse().getContentAsString();

        AuthResponseDto authResponseDto = mapper.readValue(resJson, AuthResponseDto.class);

        assertNotNull(authResponseDto.getAccessToken());
        assertNotEquals("", authResponseDto.getAccessToken());

        String newUserJson = mockMvc.perform(
                MockMvcRequestBuilders.get(USER_MAPPING + "/2")
                        .header("Authorization", authResponseDto.getAccessToken())
        ).andReturn().getResponse().getContentAsString();

        UserDto newUser = mapper.readValue(newUserJson, UserDto.class);

        assertEquals(email, newUser.getEmail());
    }

    @Test
    @Order(4)
    void get() throws Exception {
        // positive
        String resJson = mockMvc.perform(
                MockMvcRequestBuilders.get(USER_MAPPING + "/1")
                        .header("Authorization", token)
        ).andReturn().getResponse().getContentAsString();

        UserDto user = mapper.readValue(resJson, UserDto.class);

        assertEquals(userDto, user);

        // negative
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get(USER_MAPPING + "/100")
                        .header("Authorization", token)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    @Order(3)
    void update() throws Exception {
        String resJson = mockMvc.perform(
                MockMvcRequestBuilders.put(USER_MAPPING + "/1")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getUserJson())
                ).andReturn().getResponse().getContentAsString();

        UserDto user = mapper.readValue(resJson, UserDto.class);

        assertEquals("Test address from Tests city" ,user.getDeliveryAddress());
    }

    private String getUserJson() throws JsonProcessingException {
        userDto = userService.read(1L);
        userDto.setDeliveryAddress("Test address from Tests city");
        return mapper.writeValueAsString(userDto);
    }
}