package db.project.service;

import db.project.dto.*;
import db.project.exceptions.ErrorCode;
import db.project.exceptions.FavoritesException;
import db.project.repository.FavoritesRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;

    public FavoritesService(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    public FavoritesDto.FavoritesResponse locationList(FavoritesDto.FavoritesSearch favoritesSearchDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<FavoritesDto.Favorites> favoritesSearchList = favoritesRepository.locationList(favoritesSearchDto, user_id);

        FavoritesDto.FavoritesResponse response = new FavoritesDto.FavoritesResponse();
        for(FavoritesDto.Favorites favoritesSearch : favoritesSearchList) {
            response.getLocations().add(favoritesSearch);
        }
        return response;
    }

    public void locationChange(FavoritesDto.FavoritesChange favoritesChangeDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        if(favoritesChangeDto.isFavorite()) {
            favoritesRepository.locationAdd(favoritesChangeDto.getLocation(), user_id)
                    .orElseThrow(() -> new FavoritesException("ALREADY ADD FAVORITE", ErrorCode.FAVORITE_DUPLICATION));
        } else {
            favoritesRepository.locationDelete(favoritesChangeDto.getLocation(), user_id);
        }
    }

    public FavoritesDto.FavoritesResponse locationList() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<FavoritesDto.Favorites> favoritesList = favoritesRepository.locationList(user_id);

        FavoritesDto.FavoritesResponse response = new FavoritesDto.FavoritesResponse();
        for(FavoritesDto.Favorites favorite : favoritesList) {
            response.getLocations().add(favorite);
        }
        return response;
    }
}
