package db.project.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class NoticeDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class NoticeListResponse {
        private int noticeCount;
        private List<NoticeList> noticeList;

        public NoticeListResponse(int noticeCount) {
            this.noticeCount = noticeCount;
            this.noticeList = new ArrayList<>();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NoticeInfo {
        private int notice_id;
        private String user_id;
        private String title;
        private String content;
        private String date;
        private int views;
        private boolean isAuthor;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeList {
        private int notice_id;
        private String title;
        private String date;
        private int views;
    }

    @Getter
    public static class NoticeCreateAndUpdate {
        private String title;
        private String content;
    }

    @Getter
    public static class NoticeDelete {
        private int notice_id;
    }
}
