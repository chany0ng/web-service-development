package db.project.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginRequest {
    String userId;
    String password;
}
