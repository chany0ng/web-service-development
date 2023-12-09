package db.project.service;

import db.project.dto.*;
import db.project.exceptions.ErrorCode;
import db.project.exceptions.MapException;
import db.project.repository.MapRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MapService {
    private final MapRepository mapRepository;

    public MapService(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    public MapLocationListResponseDto locationList(){
       List<ReturnGetMapLocationDto> mapLocationList = mapRepository.locationList();
       MapLocationListResponseDto response = new MapLocationListResponseDto();
       for (ReturnGetMapLocationDto mapLocation : mapLocationList) {
           response.getLocations().add(mapLocation);
        }
       return response;
    }

    @Transactional
    public Optional<MapLocationInfoResponseDto> locationInfo(PostMapLocationInfoDto postMapLocationInfoDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<ReturnPostMapLocationInfoDto> locationInfoOptional = mapRepository.locationInfo(postMapLocationInfoDto);
        if(locationInfoOptional.isEmpty()) {
            throw new MapException("FAILURE VIEW INFO", ErrorCode.FAIL_MAP_INFO);
        }
        Optional<Boolean> isFavorite = mapRepository.getIsFavorite(locationInfoOptional.get().getLocation_id(), user_id);

        return locationInfoOptional.map(mapLocationInfo -> {
            MapLocationInfoResponseDto response = new MapLocationInfoResponseDto(mapLocationInfo.getLocation_id(), mapLocationInfo.getAddress(), mapLocationInfo.getLocation_status(), isFavorite.get());

            if(mapLocationInfo.getBike_id() != null) {
                String[] bikeId = mapLocationInfo.getBike_id().split(",");
                String[] bikeStatus = mapLocationInfo.getBike_status().split(",");

                for (int i = 0; i < bikeId.length; i++) {
                    response.getBike().add(new BikeInfo(bikeId[i], bikeStatus[i]));
                }
            }
            return response;
        });
    }
}
