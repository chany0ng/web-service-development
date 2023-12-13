package db.project.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnGetNoticeInfoDto {
    private int notice_id;
    private String user_id;
    private String title;
    private String content;
    private String date;
    private int views;
    private boolean isAuthor;
}
