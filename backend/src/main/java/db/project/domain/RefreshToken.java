package db.project.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshToken {
    String id;
    String refreshToken;
}