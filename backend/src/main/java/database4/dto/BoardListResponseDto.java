package database4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BoardListResponseDto {
    List<ReturnPostBoardListDto> boardList;

    public BoardListResponseDto() {
        this.boardList = new ArrayList<>();
    }
}
