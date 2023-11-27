package database4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRentalRentDto {
    private String bike_id;
//    private String user_id;
    private String start_location;

    public PostRentalRentDto() {
    }

    public PostRentalRentDto(String bike_id, String start_location) {
        this.bike_id = bike_id;
//        this.user_id = user_id;
        this.start_location = start_location;
    }
}
