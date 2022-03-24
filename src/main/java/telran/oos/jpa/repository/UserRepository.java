package telran.oos.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.oos.jpa.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
     User findByEmail(String email);
}
