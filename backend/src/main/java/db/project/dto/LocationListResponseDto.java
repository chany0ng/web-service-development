package db.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LocationListResponseDto {
    private int locationCount;
    List<ReturnGetLocationListDto> locationList;

    public LocationListResponseDto(int locationCount) {
        this.locationCount = locationCount;
        this.locationList = new ArrayList<>();
    }
}
