package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnGetBreakdownReportListDto {
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
