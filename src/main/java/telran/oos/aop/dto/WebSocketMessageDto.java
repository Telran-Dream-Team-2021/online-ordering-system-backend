package telran.oos.aop.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WebSocketMessageDto {
    String action;
    Long entityId;
}
