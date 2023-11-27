package database4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnGetRankTimeDto {
    private int ranking;
    private String user_id;
    private String duration_time;

    public ReturnGetRankTimeDto() {
    }

    public ReturnGetRankTimeDto(int ranking, String user_id, String duration_time) {
        this.ranking = ranking;
        this.user_id = user_id;
        this.duration_time = duration_time;
    }
}
