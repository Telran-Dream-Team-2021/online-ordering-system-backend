package telran.oos.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import telran.oos.jpa.entity.User;

public interface UserService extends UserDetailsService {
    User read(Long id);
    User read(String email);

}
