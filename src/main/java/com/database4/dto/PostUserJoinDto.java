package com.database4.dto;

import lombok.Getter;

@Getter
public class PostUserJoinDto {

    private String user_id;
    private String password;
    private String email;
    private String phone_number;
    private String pw_answer;
    private String content;
}
