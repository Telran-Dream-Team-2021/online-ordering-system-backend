package telran.oos.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import telran.oos.api.dto.AuthRequestDto;
import telran.oos.api.dto.UserDto;
import telran.oos.jpa.entity.User;

public interface UserService extends UserDetailsService {
    UserDto read(Long id);
    User read(String email);
    User create(AuthRequestDto loginData);
    UserDto update(Long id, UserDto userDto);
}
