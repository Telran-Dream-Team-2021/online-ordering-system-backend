package telran.oos.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.oos.jpa.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}