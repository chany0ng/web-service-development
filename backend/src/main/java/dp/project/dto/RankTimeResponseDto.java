package dp.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RankTimeResponseDto {
    private int user_ranking;
    private String user_id;
    private String user_duraiton_time;

    private List<ReturnGetRankTimeDto> rank;

    public RankTimeResponseDto() {
        this.rank = new ArrayList<>();
    }

    public RankTimeResponseDto(int user_ranking, String user_id, String user_duraiton_time) {
        this.user_ranking = user_ranking;
        this.user_id = user_id;
        this.user_duraiton_time = user_duraiton_time;
        this.rank = new ArrayList<>();
    }
}
