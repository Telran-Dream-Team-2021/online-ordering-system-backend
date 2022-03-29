package telran.oos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import telran.exceptions.ResourceNotFoundException;
import telran.oos.api.dto.ProductDto;
import telran.oos.api.dto.Roles;
import telran.oos.api.dto.UserDto;
import telran.oos.jpa.entity.Role;
import telran.oos.jpa.entity.User;
import telran.oos.service.UserService;

import java.util.Objects;

import static telran.oos.api.ApiConstants.USER_MAPPING;

@Slf4j
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();

            if (Objects.equals(user.getId(), id) || user.isAdmin()) {
                log.info("Getting userData with id = {} | {}", id, user.getRoles().stream().map(Role::getName).toList());
                return service.read(id);
            }
        }

        throw new AccessDeniedException("Access denied");
    }
}
