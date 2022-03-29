package telran.oos.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import telran.oos.api.dto.OrderDto;
import telran.oos.jpa.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query("select new telran.oos.api.dto.OrderDto(id, orderItems.stream().map(i->new OrderItemDto()), user.id, deliveryAddress, status, deliveryDate, lastEditionDate) from Order where id = :id")
//    OrderDto getOrder(@Param("id") Long id);
}
