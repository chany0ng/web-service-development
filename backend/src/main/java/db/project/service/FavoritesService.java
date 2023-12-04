package db.project.service;

import db.project.dto.FavoritesResponseDto;
import db.project.dto.PostFavoritesChangeDto;
import db.project.dto.PostFavoritesSearchDto;
import db.project.dto.ReturnFavoritesDto;
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

    public FavoritesResponseDto locationList(PostFavoritesSearchDto postFavoritesSearchDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<ReturnFavoritesDto> favoritesSearchList = favoritesRepository.locationList(postFavoritesSearchDto, user_id);

        FavoritesResponseDto response = new FavoritesResponseDto();
        for(ReturnFavoritesDto favoritesSearch : favoritesSearchList) {
            response.getLocations().add(favoritesSearch);
        }
        return response;
    }

    public void locationChange(PostFavoritesChangeDto postFavoritesChangeDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        if(postFavoritesChangeDto.isFavorite()) {
            favoritesRepository.locationAdd(postFavoritesChangeDto.getLocation(), user_id)
                    .orElseThrow(() -> new FavoritesException("ALREADY ADD FAVORITE", ErrorCode.FAVORITE_DUPLICATION));
        } else {
            favoritesRepository.locationDelete(postFavoritesChangeDto.getLocation(), user_id);
        }
    }

    public FavoritesResponseDto locationList() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<ReturnFavoritesDto> favoritesList = favoritesRepository.locationList(user_id);

        FavoritesResponseDto response = new FavoritesResponseDto();
        for(ReturnFavoritesDto favorite : favoritesList) {
            response.getLocations().add(favorite);
        }
        return response;
    }
}
