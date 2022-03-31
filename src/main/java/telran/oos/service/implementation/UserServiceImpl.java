package telran.oos.service.implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.exceptions.ResourceNotFoundException;
import telran.oos.aop.inter.WebSocketMessagable;
import telran.oos.api.dto.AuthRequestDto;
import telran.oos.api.dto.Roles;
import telran.oos.api.dto.UserDto;
import telran.oos.jpa.entity.Role;
import telran.oos.jpa.entity.User;
import telran.oos.jpa.repository.UserRepository;
import telran.oos.service.UserService;

import java.util.Collections;

import static telran.oos.api.ApiConstants.WEBSOCKET_USER_THEME;

@Service
public class UserServiceImpl implements UserService, WebSocketMessagable {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDto read(Long id) {
        UserDto userDto = userRepository.getUserById(id);

        if (userDto == null) {
            throw new ResourceNotFoundException(String.format("User with id %d not found.", id));
        }

        return userDto;
    }

    @Override
    public User read(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User create(AuthRequestDto request) {
        String email = request.getEmail();
        User userFromDB = userRepository.findByEmail(email);

        if (userFromDB != null) {
            return userFromDB;
        }

        User user = new User();
        user.setEmail(email);
        user.setDisplayName(email.substring(0, email.indexOf("@")));
        user.setRoles(Collections.singleton(new Role(1L, Roles.ROLE_USER.toString())));
        user.setHashPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        user.setDeliveryAddress(userDto.getDeliveryAddress());
        user.setEmail(userDto.getEmail());
        user.setDisplayName(userDto.getDisplayName());

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = read(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public String getTheme() {
        return WEBSOCKET_USER_THEME;
    }
}
