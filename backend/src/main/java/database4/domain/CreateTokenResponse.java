package database4.domain;


import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class CreateTokenResponse {
    private String accessToken;
}
