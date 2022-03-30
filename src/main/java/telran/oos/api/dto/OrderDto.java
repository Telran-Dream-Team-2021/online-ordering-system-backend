package telran.oos.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import telran.oos.utils.JsonDateDeserializer;
import telran.oos.utils.JsonDateSerializer;

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
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private LocalDateTime deliveryDate;
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private LocalDateTime lastEditionDate;

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", orderItems=" + orderItems.size() +
                ", userId=" + userId +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", status=" + status +
                ", deliveryDate=" + deliveryDate +
                ", lastEditionDate=" + lastEditionDate +
                '}';
    }
}
