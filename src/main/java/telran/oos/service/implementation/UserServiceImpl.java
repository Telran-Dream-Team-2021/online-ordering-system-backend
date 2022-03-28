package telran.oos.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import telran.oos.api.dto.UserDto;
import telran.oos.jpa.entity.User;
import telran.oos.jpa.repository.UserRepository;
import telran.oos.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDto read(Long id) {
        return repository.getUserById(id);
    }

    @Override
    public User read(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return read(username);
    }
}
