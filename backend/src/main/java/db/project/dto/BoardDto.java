package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class BoardDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class BoardListResponse {
        private int boardCount;
        private List<BoardDto.BoardList> boardList;

        public BoardListResponse(int boardCount) {
            this.boardCount = boardCount;
            this.boardList = new ArrayList<>();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BoardInfo {
        private int board_id;
        private String user_id;
        private String title;
        private String content;
        private String date;
        private int views;
        private boolean isAuthor;
        List<BoardCommentDto.BoardComment> comments;

        public BoardInfo(int board_id, String user_id, String title, String content, String date, int views, boolean isAuthor) {
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

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardList {
        private int board_id;
        private String title;
        private String date;
        private int views;
    }

    @Getter
    public static class BoardCreateAndUpdate {
        private String title;
        private String content;
    }

    @Getter
    public static class BoardDelete {
        private int board_id;
    }
}
