package db.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LocationListResponseDto {
    List<ReturnGetMapLocationDto> locations;

    public LocationListResponseDto() {
        this.locations = new ArrayList<>();
    }

    public LocationListResponseDto(List<ReturnGetMapLocationDto> locations) {
        this.locations = locations;
    }
}
