package db.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostBreakdownReportDto {
    private String bike_id;
    private Boolean tire;
    private Boolean chain;
    private Boolean saddle;
    private Boolean pedal;
    private Boolean terminal;
}
