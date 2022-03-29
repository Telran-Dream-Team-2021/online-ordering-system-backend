package telran.oos.api.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull
    private Long uid;
    @NotEmpty
    private String displayName;
    @NotEmpty
    private String deliveryAddress;
    @NotNull
    private String email;
}
