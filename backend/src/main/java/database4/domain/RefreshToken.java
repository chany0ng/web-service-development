package database4.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshToken {
    String id;
    String refreshToken;
}
