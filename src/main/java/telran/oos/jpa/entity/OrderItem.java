package telran.oos.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_items")
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

    public OrderItem() {
    }

    public OrderItem(Order order, Product product) {
        this.order = order;
        this.product = product;
    }


}
