package db.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnGetRankCountDto {
    private int ranking;
    private String user_id;
    private int using_count;

    public ReturnGetRankCountDto() {
    }

    public ReturnGetRankCountDto(int ranking, String user_id, int using_count) {
        this.ranking = ranking;
        this.user_id = user_id;
        this.using_count = using_count;
    }
}
