package telran.oos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import telran.oos.api.dto.UserDto;
import telran.oos.jpa.entity.User;
import telran.oos.service.UserService;

import java.util.Objects;

import static telran.oos.api.ApiConstants.USER_MAPPING;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping(USER_MAPPING)
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        if (wrongAuth(id)) {
            throw new AccessDeniedException("Access denied");
        }

        log.debug("Getting userData with id = {}", id);
        return service.read(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto) {
        if (wrongAuth(id)) {
            throw new AccessDeniedException("Access denied");
        }

        log.debug("Updating userData with id = {} and address = {}", id, userDto.getDeliveryAddress());
        return service.update(id, userDto);
    }

    private boolean wrongAuth(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();

            return !Objects.equals(user.getId(), id) && !user.isAdmin();
        }

        return true;
    }
}
