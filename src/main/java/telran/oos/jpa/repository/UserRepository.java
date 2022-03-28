package telran.oos.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import telran.oos.api.dto.UserDto;
import telran.oos.jpa.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
     User findByEmail(String email);

     @Query("select new telran.oos.api.dto.UserDto(id, role, displayName, deliveryAddress, email) from User where id = :id")
     UserDto getUserById(@Param("id") Long id);
}
