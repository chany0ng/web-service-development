package db.project.domain;


import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class CreateTokenResponse {
    private String accessToken;
}
