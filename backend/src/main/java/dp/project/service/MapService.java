package dp.project.service;

import database4.dto.*;
import dp.project.dto.*;
import dp.project.repository.MapRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MapService {
    private final MapRepository mapRepository;

    public MapService(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    public LocationListResponseDto locationList(){
       List<ReturnGetMapLocationDto> mapLocationList = mapRepository.locationList();
       LocationListResponseDto response = new LocationListResponseDto();
       for (ReturnGetMapLocationDto mapLocation : mapLocationList) {
           response.getLocations().add(mapLocation);
        }
       return response;
    }

    public Optional<LocationInfoResponseDto> locationInfo(PostMapLocationInfoDto postMapLocationInfoDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<ReturnPostMapLocationInfoDto> locationInfoOptional = mapRepository.locationInfo(postMapLocationInfoDto, user_id);

        return locationInfoOptional.map(mapLocationInfo -> {
            LocationInfoResponseDto response = new LocationInfoResponseDto(mapLocationInfo.getLocation_id(), mapLocationInfo.getAddress(), mapLocationInfo.getLocation_status(), mapLocationInfo.isFavorite());
            String[] bikeId = mapLocationInfo.getBike_id().split(",");
            String[] bikeStatus = mapLocationInfo.getBike_status().split(",");

            for (int i = 0; i < bikeId.length; i++) {
                response.getBike().add(new BikeInfo(bikeId[i], bikeStatus[i]));
            }

            return response;
        });
    }
}
