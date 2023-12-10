package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnGetBoardAndNoticeListDto {
    private String title;
    private String date;
    private int views;
}
