package telran.oos.api.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private String accessToken;
    private Roles role;
    private String displayName;
    private Long uid;
}
