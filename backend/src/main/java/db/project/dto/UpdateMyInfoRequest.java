package db.project.dto;

import lombok.Getter;

@Getter
public class UpdateMyInfoRequest {
    private String password;
    private String email;
    private String phone_number;
}
