package db.project.domain;

import lombok.Getter;

@Getter
public class CreateTokenRequest {
    private String refreshToken;
}