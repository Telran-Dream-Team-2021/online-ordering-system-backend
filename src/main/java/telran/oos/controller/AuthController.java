package telran.oos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import telran.oos.api.dto.AuthRequestDto;
import telran.oos.api.dto.AuthResponseDto;
import telran.oos.api.dto.Roles;
import telran.oos.jpa.entity.User;
import telran.oos.security.JwtUtils;
import telran.oos.service.UserService;

import java.util.List;

import static telran.oos.api.ApiConstants.LOGIN_MAPPING;

@Slf4j
@RestController
@RequestMapping(LOGIN_MAPPING)
@CrossOrigin
@Validated
public class AuthController {
    UserService service;
    PasswordEncoder passwordEncoder;
    JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserService service, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    ResponseEntity<?> login(@RequestBody AuthRequestDto loginData) {
        User user = service.read(loginData.getEmail());
        log.debug("Logging in with email: {}", loginData.getEmail());

        if (null == user) {
            user = service.create(loginData);
        }

        if (null == user || !passwordEncoder.matches(loginData.getPassword(), user.getHashPassword())) {
            return wrongAccount();
        }

        return ResponseEntity.ok(getToken(loginData, user));
    }

    private AuthResponseDto getToken(AuthRequestDto loginData, User user) {
        String accessToken = "Bearer " + this.jwtUtils.create(loginData.getEmail());
        log.debug("Login success");

        List<Roles> roles = user.getRoles()
            .stream()
            .map(role -> Roles.valueOf(role.getName()))
            .toList();

        return new AuthResponseDto(accessToken, roles, user.getDisplayName(), user.getId());
    }

    private ResponseEntity<?> wrongAccount() {
        log.error("Logging is failed");

        return ResponseEntity.badRequest().body("");
    }
}
