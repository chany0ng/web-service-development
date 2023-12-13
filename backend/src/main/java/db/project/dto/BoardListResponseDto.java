package db.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardListResponseDto {
    private int boardCount;
    private List<ReturnGetBoardListDto> boardList;

    public BoardListResponseDto(int boardCount) {
        this.boardCount = boardCount;
        this.boardList = new ArrayList<>();
    }
}
