package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnGetBoardInfoDto {
    private String user_id;
    private String title;
    private String content;
    private String date;
    private int views;
    private boolean isAuthor;
}
