package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class FavoritesDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class FavoritesResponse {
        private List<FavoritesDto.Favorites> locations;

        public FavoritesResponse() {
            this.locations = new ArrayList<>();
        }

    }

    @Getter
    public static class FavoritesChange {
        private String location;
        private boolean favorite;
    }

    @Getter
    public static class FavoritesSearch {
        private String location;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Favorites {
        private String location_id;
        private String address;
        private boolean favorite;

    }
}
