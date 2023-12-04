package dp.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RankCountResponseDto {
    private int user_ranking;
    private String user_id;
    private int user_using_count;

    private List<ReturnGetRankCountDto> rank;

    public RankCountResponseDto() {
        this.rank = new ArrayList<>();
    }

    public RankCountResponseDto(int user_ranking, String user_id, int user_using_count) {
        this.user_ranking = user_ranking;
        this.user_id = user_id;
        this.user_using_count = user_using_count;
        this.rank = new ArrayList<>();
    }
}
