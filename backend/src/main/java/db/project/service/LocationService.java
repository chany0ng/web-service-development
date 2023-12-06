package db.project.service;

import db.project.dto.*;
import db.project.exceptions.ErrorCode;
import db.project.exceptions.LocationException;
import db.project.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    @Transactional
    public LocationListResponseDto locationList(int page) {
        page = (page - 1) * 10;
        Optional<List<ReturnGetLocationListDto>> locationListOptional = locationRepository.locationList(page);
        if(locationListOptional.isEmpty()) {
            throw new LocationException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int locationCount = locationRepository.getLocationCount();
        if(page != 0 && locationCount <= page) {
            throw new LocationException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        List<ReturnGetLocationListDto> locationList = locationListOptional.get();
        LocationListResponseDto response = new LocationListResponseDto(locationCount);
        for (ReturnGetLocationListDto location : locationList) {
            response.getLocationList().add(location);
        }

        return response;
    }

    public void locationCreate(PostLocationCreateDto postLocationCreateDto) {
        Optional<String> exceptionCheck = locationRepository.locationCreate(postLocationCreateDto);
        if(exceptionCheck.isEmpty()) {
            throw new LocationException("LOCATION ID DUPLICATE", ErrorCode.LOCATION_DUPLICATION);
        }
    }

    public void locationDelete(PostLocationDeleteDto postLocationDeleteDto) {
        int deleteCheck = locationRepository.locationDelete(postLocationDeleteDto);
        if(deleteCheck == 0) {
            throw new LocationException("NON EXIST LOCATION ID", ErrorCode.NON_EXIST_LOCATION);
        }
    }
}
