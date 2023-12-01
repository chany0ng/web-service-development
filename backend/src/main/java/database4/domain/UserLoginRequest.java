package database4.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginRequest {
    String id;
    String password;
}
