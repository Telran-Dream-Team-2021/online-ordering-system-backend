package telran.oos.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    // TODO
    private String email;
    private String password;
    private String provider;
}
