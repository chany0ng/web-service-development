package db.project.dto;

import lombok.Getter;

@Getter
public class UpdatePWRequest {
    private String id;
    private String new_password;
}
