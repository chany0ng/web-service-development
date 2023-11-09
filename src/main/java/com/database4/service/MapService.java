package com.database4.service;

import com.database4.dto.*;
import com.database4.repository.MapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {
    private final MapRepository mapRepository;

    public MapService(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    public LocationListResponseDto locationList(){
       List<ReturnGetMapLocationDto> mapLocationList = mapRepository.locationList();
       LocationListResponseDto response = new LocationListResponseDto();
       for(ReturnGetMapLocationDto mapLocation : mapLocationList){
           response.getLocations().add(mapLocation);
       }
       return response;
    }

    public LocationInfoResponseDto locationInfo(PostMapLocationInfoDto postMapLocationInfoDto) {
        ReturnPostMapLocationInfoDto LocationInfo = mapRepository.locationInfo(postMapLocationInfoDto);

        LocationInfoResponseDto response = new LocationInfoResponseDto(LocationInfo.getLocation_id(), LocationInfo.getAddress(), LocationInfo.getLocation_status(), LocationInfo.isFavorite());
        String[] bikeId = LocationInfo.getBike_id().split(",");
        String[] bikeStatus = LocationInfo.getBike_status().split(",");

        for (int i = 0; i < bikeId.length; i++) {
            response.getBike().add(new BikeInfo(bikeId[i], bikeStatus[i]));
        }

        return response;
    }
}
