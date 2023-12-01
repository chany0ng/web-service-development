package database4.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserLoginResponse {
    private String accessToken;
    private String refreshToken;
    private String role;
}
