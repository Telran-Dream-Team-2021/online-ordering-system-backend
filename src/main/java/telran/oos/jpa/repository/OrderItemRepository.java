package telran.oos.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.oos.jpa.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
