package telran.oos.api.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketDto {
    // TODO validation rules
    private Long id;
    private Long userId;
    private List<OrderItemDto> basketItems;
}
