package telran.oos.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    // TODO validation rules and username -> id
    private Long uid;
    private String displayName;
    private String deliveryAddress;
    private String email;
}
