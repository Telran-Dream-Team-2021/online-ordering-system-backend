package telran.oos.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    // TODO validation rules
    private Long id;
    private Long orderId;
    private Long productId;
    private Float pricePerUnit;
    private int quantity;
}
