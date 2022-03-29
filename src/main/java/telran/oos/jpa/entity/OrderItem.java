package telran.oos.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order-items")
public class OrderItem {
    @Id
    private Long id;

    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;

    @Column
    private Float pricePerUnit;
    @Column
    private int quantity;
}
