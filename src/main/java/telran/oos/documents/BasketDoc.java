package telran.oos.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import telran.oos.api.dto.OrderItemDto;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "baskets")
public class BasketDoc {
    @NonNull
    private long id;
    @NonNull
    private List<OrderItemDto> basketItems = new ArrayList<>();

    @NonNull
    private long userId;

    @Override
    public String toString() {
        return "BasketDoc{" +
                "id=" + id +
                ", basketItems=" + basketItems +
                ", userId=" + userId +
                '}';
    }
}
