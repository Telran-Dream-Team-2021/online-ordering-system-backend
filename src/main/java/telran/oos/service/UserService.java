package telran.oos.service;

import telran.oos.jpa.entity.User;

public interface UserService {
    User read(Long id);
    User read(String email);

}
