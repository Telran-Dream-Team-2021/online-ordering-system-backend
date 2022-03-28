package telran.oos.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.oos.jpa.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
