package telran.oos.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import telran.oos.api.dto.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
                ", user=" + user.getEmail() +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", status=" + status +
                ", deliveryDate=" + deliveryDate +
                ", lastEditionDate=" + lastEditionDate +
                '}';
    }
}
