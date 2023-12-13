package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnGetBoardInfoDto {
    private int board_id;
    private String user_id;
    private String title;
    private String content;
    private String date;
    private int views;
    private boolean isAuthor;
    List<GetBoardCommentDto> comments;

    public ReturnGetBoardInfoDto(int board_id, String user_id, String title, String content, String date, int views, boolean isAuthor) {
        this.board_id = board_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.views = views;
        this.isAuthor = isAuthor;
        this.comments = new ArrayList<>();
    }
}
