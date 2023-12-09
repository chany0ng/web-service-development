package db.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BreakdownReportListResponseDto {
    private int reportCount;
    private List<ReturnGetBreakdownReportListDto> reportList;

    public BreakdownReportListResponseDto(int reportCount) {
        this.reportCount = reportCount;
        this.reportList = new ArrayList<>();
    }
}
