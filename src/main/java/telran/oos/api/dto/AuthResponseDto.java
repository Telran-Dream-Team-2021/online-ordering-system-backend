package telran.oos.api.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private String accessToken;
    private List<Roles> roles;
    private String displayName;
    private Long uid;
}
