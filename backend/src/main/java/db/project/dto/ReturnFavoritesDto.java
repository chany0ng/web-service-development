package db.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnFavoritesDto {
    private String location_id;
    private String address;
    private boolean favorite;

    public ReturnFavoritesDto() {
    }

    public ReturnFavoritesDto(String location_id, String address, boolean favorite) {
        this.location_id = location_id;
        this.address = address;
        this.favorite = favorite;
    }
}
