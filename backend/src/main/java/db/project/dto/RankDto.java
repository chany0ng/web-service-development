package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class RankDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RankCount {
        private int ranking;
        private String user_id;
        private int using_count;
    }

    @Getter
    @Setter
    public static class RankCountResponse {
        private int user_ranking;
        private String user_id;
        private int user_using_count;

        private List<RankCount> rank;

        public RankCountResponse() {
            this.rank = new ArrayList<>();
        }

        public RankCountResponse(int user_ranking, String user_id, int user_using_count) {
            this.user_ranking = user_ranking;
            this.user_id = user_id;
            this.user_using_count = user_using_count;
            this.rank = new ArrayList<>();
        }
    }

    @Getter
    @Setter
    public static class RankTimeResponse {
        private int user_ranking;
        private String user_id;
        private String user_duraiton_time;

        private List<RankTime> rank;

        public RankTimeResponse() {
            this.rank = new ArrayList<>();
        }

        public RankTimeResponse(int user_ranking, String user_id, String user_duraiton_time) {
            this.user_ranking = user_ranking;
            this.user_id = user_id;
            this.user_duraiton_time = user_duraiton_time;
            this.rank = new ArrayList<>();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RankTime {
        private int ranking;
        private String user_id;
        private String duration_time;
    }
}
