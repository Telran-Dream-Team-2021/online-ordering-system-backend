package telran.oos.api.dto;


import lombok.*;
import telran.oos.aop.inter.Idable;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketDto implements Idable {
    @NonNull
    private Long id;
    @NonNull
    private Long userId;
    private List<OrderItemDto> basketItems;
}
