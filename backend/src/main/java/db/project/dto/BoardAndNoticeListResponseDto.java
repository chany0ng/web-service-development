package db.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardAndNoticeListResponseDto {
    private int boardCount;
    private List<ReturnGetBoardAndNoticeListDto> boardAndNoticeList;

    public BoardAndNoticeListResponseDto(int boardCount) {
        this.boardCount = boardCount;
        this.boardAndNoticeList = new ArrayList<>();
    }
}
