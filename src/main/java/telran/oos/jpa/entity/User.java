package telran.oos.jpa.entity;

import lombok.*;
import telran.oos.api.dto.Roles;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private String hashPassword;
    @Enumerated(EnumType.STRING)
    private Roles role;
    private String displayName;
    private String deliveryAddress;
    private String email;
}
