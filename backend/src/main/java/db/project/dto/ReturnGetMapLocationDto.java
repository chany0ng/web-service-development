package db.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnGetMapLocationDto {
    private int bikeCount;
    private String latitude;
    private String longitude;
    private String address;

    public ReturnGetMapLocationDto() {
    }

    public ReturnGetMapLocationDto(String latitude, String longitude, int bikeCount, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.bikeCount = bikeCount;
        this.address = address;
    }
}
