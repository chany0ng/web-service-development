package db.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnGetMapLocationDto {
    private int bikeCount;
    private String latitude;
    private String longitude;

    public ReturnGetMapLocationDto() {
    }

    public ReturnGetMapLocationDto(String latitude, String longitude, int bikeCount) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.bikeCount = bikeCount;
    }
}
