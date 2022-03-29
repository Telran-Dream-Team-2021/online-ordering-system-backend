package telran.oos.service.implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import telran.oos.api.dto.AuthRequestDto;
import telran.oos.api.dto.Roles;
import telran.oos.api.dto.UserDto;
import telran.oos.jpa.entity.Role;
import telran.oos.jpa.entity.User;
import telran.oos.jpa.repository.RoleRepository;
import telran.oos.jpa.repository.UserRepository;
import telran.oos.service.UserService;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDto read(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User read(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean create(AuthRequestDto request) {
        String email = request.getEmail();
        User userFromDB = userRepository.findByEmail(email);

        if (userFromDB != null) {
            return false;
        }

        User user = new User();
        user.setEmail(email);
        user.setDisplayName(email.substring(0, email.indexOf("@")));
        user.setRoles(Collections.singleton(new Role(1L, Roles.ROLE_USER.toString())));
        user.setHashPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = read(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
