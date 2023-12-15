package db.project.dto;

import lombok.Getter;

@Getter
public class FindPWRequest {
    private String id;
    private int pw_question;
    private String pw_answer;
}
