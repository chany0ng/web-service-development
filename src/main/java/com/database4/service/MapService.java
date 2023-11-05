package com.database4.service;

import com.database4.dto.PostGetMapLocationInfoDto;
import com.database4.dto.ReturnGetMapLocationDto;
import com.database4.dto.ReturnGetMapLocationInfoDto;
import com.database4.repository.MapRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapService {
    private MapRepository mapRepository;

    public MapService(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    public List<ReturnGetMapLocationDto> locationList(){
        return mapRepository.locationList();

    }

    public ReturnGetMapLocationInfoDto locationInfo(PostGetMapLocationInfoDto postGetMapLocationInfoDto){
        return mapRepository.locationInfo(postGetMapLocationInfoDto);
    }
}
