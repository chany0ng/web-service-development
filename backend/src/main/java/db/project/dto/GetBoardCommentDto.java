package db.project.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardCommentDto {
    private int comment_id;
    private String content;
    private String date;
    private String user_id;
}
