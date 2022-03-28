package telran.oos.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    // TODO validation rules and username -> id
    private Long uid;
    private boolean isAdmin;
    private String displayName;
    private String deliveryAddress;
    private String email;

    public UserDto(Long id, Roles role, String displayName, String deliveryAddress, String email) {
        this.uid = id;
        this.isAdmin = role == Roles.ADMIN;
        this.displayName = displayName;
        this.deliveryAddress = deliveryAddress;
        this.email = email;
    }
}
