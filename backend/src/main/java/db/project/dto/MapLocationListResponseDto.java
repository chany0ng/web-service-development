package db.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MapLocationListResponseDto {
    List<ReturnGetMapLocationDto> locations;

    public MapLocationListResponseDto() {
        this.locations = new ArrayList<>();
    }

    public MapLocationListResponseDto(List<ReturnGetMapLocationDto> locations) {
        this.locations = locations;
    }
}
