package telran.oos.api.dto;

import lombok.*;
import telran.oos.aop.inter.Idable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto implements Idable {
    // TODO validation rules
    private Long id;
    private Long orderId;
    private Long productId;
    private Float pricePerUnit;
    private int quantity;
}
