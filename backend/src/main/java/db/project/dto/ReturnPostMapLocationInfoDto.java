package db.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnPostMapLocationInfoDto {
    private String location_id;
    private String address;
    private String location_status;
    private String bike_id;
    private String bike_status;
    private boolean favorite;

    public ReturnPostMapLocationInfoDto() {
    }

    public ReturnPostMapLocationInfoDto(String location_id, String address, String location_status, String bike_id, String bike_status, boolean favorite) {
        this.location_id = location_id;
        this.address = address;
        this.location_status = location_status;
        this.bike_id = bike_id;
        this.bike_status = bike_status;
        this.favorite = favorite;
    }
}
