package telran.oos.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import telran.oos.aop.inter.Idable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
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
