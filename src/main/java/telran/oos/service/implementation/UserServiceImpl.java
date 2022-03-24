package telran.oos.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public User read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User read(String email) {
        return repository.findByEmail(email);
    }
}
