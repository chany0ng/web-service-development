package db.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FavoritesResponseDto {
    List<ReturnFavoritesDto> locations;

    public FavoritesResponseDto() {
        this.locations = new ArrayList<>();
    }

    public FavoritesResponseDto(List<ReturnFavoritesDto> locations) {
        this.locations = locations;
    }
}
