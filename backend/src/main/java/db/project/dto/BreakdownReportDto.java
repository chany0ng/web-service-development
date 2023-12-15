package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class BreakdownReportDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BreakdownReportListResponse {
        private int reportCount;
        private List<BreakdownReportList> reportList;

        public BreakdownReportListResponse(int reportCount) {
            this.reportCount = reportCount;
            this.reportList = new ArrayList<>();
        }
    }

    @Getter
    @Setter
    public static class Report {
        private String bike_id;
        private Boolean tire;
        private Boolean chain;
        private Boolean saddle;
        private Boolean pedal;
        private Boolean terminal;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BreakdownReportList {
        private String user_id;
        private Boolean tire;
        private Boolean chain;
        private Boolean saddle;
        private Boolean pedal;
        private Boolean terminal;
        private String created_at;
        private String bike_id;
        private String status;
    }

    @Getter
    public static class BreakdownReportRepair {
        private String bike_id;
    }
}
