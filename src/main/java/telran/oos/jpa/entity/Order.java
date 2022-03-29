package telran.oos.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import telran.oos.api.dto.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    @OneToMany
    private List<OrderItem> orderItems;
    @ManyToOne
    private User user;
    @Column
    private String deliveryAddress;
    @Column
    private OrderStatus status;
    @Column
    private LocalDateTime deliveryDate;
    @Column
    private LocalDateTime lastEditionDate;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderItems=" + orderItems.size() +
                ", user=" + user +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", status=" + status +
                ", deliveryDate=" + deliveryDate +
                ", lastEditionDate=" + lastEditionDate +
                '}';
    }
}
