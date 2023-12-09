package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BikeListResponseDto {
    private int bikeCount;
    List<ReturnGetBikeListDto> bikeList;

    public BikeListResponseDto(int bikeCount) {
        this.bikeCount = bikeCount;
        this.bikeList = new ArrayList<>();
    }
}
