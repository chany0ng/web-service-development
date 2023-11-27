package database4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnPostRentalHistoryDto {
    private String bike_id;
    private String start_time;
    private String start_location;
    private String  end_time;
    private String end_location;

    public ReturnPostRentalHistoryDto() {
    }

    public ReturnPostRentalHistoryDto(String bike_id, String  start_time, String start_location, String  end_time, String end_location) {
        this.bike_id = bike_id;
        this.start_time = start_time;
        this.start_location = start_location;
        this.end_time = end_time;
        this.end_location = end_location;
    }
}
