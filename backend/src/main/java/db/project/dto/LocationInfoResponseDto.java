package db.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LocationInfoResponseDto {  // 응답 형식에 맞춰 locationInfo 매서드의 응답객체
    private String location_id;
    private String address;
    private String location_status;
    private boolean favorite;
    private List<BikeInfo> bike;

    public LocationInfoResponseDto() {
    }

    public LocationInfoResponseDto(String location_id, String address, String location_status, boolean favorite) {
        this.location_id = location_id;
        this.address = address;
        this.location_status = location_status;
        this.favorite = favorite;
        this.bike = new ArrayList<>();
    }
}
