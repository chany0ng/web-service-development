package com.database4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRentalReturnDto {
    private String bike_id;
    private String end_location;
    private String user_id;

    public PostRentalReturnDto() {
    }

    public PostRentalReturnDto(String bike_id, String end_location, String user_id) {
        this.bike_id = bike_id;
        this.end_location = end_location;
        this.user_id = user_id;
    }
}
