package telran.oos.api.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    // TODO validation rules
    private Long id;
    private List<OrderItemDto> orderItems;
    private Long userId;
    private String deliveryAddress;
    private OrderStatus status;
    private LocalDateTime deliveryDate;
    private LocalDateTime lastEditionDate;
}
