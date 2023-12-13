package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BoardCommentDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BoardComment {
        private int comment_id;
        private String content;
        private String date;
        private String user_id;
    }

    @Getter
    public static class BoardCommentCreateAndUpdate {
        private String content;
    }

    @Getter
    public static class BoardCommentDelete {
        private int board_id;
        private int comment_id;
    }
}
