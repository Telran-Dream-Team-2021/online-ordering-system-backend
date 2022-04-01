package telran.oos.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private Long orderId;
    @NonNull
    private Long productId;
    @NonNull
    private Float pricePerUnit;
    @NonNull
    private int quantity;
}
