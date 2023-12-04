package db.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnFavoritesDto {
    private String address;
    private boolean favorite;

    public ReturnFavoritesDto() {
    }

    public ReturnFavoritesDto(String address, boolean favorite) {
        this.address = address;
        this.favorite = favorite;
    }
}
