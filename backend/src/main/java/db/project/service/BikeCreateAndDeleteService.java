package db.project.service;

import db.project.dto.*;
import db.project.exceptions.BikeCreateAndDeleteException;
import db.project.exceptions.ErrorCode;
import db.project.repository.BikeCreateAndDeleteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
@AllArgsConstructor
public class BikeCreateAndDeleteService {
    private final BikeCreateAndDeleteRepository bikeCreateAndDeleteRepository;

    public BikeListResponseDto bikeList(int page) {
        page = (page - 1) * 10;
        Optional<List<ReturnGetBikeListDto>> bikeListOptional = bikeCreateAndDeleteRepository.bikeList(page);
        if(bikeListOptional.isEmpty()) {
            throw new BikeCreateAndDeleteException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int bikeCount = bikeCreateAndDeleteRepository.getBikeCount();
        if(page != 0 && bikeCount <= page) {
            throw new BikeCreateAndDeleteException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        List<ReturnGetBikeListDto> bikeList = bikeListOptional.get();
        BikeListResponseDto response = new BikeListResponseDto(bikeCount);
        for (ReturnGetBikeListDto bike : bikeList) {
            response.getBikeList().add(bike);
        }

        return response;
    }

    public void bikeCrete(PostBikeCreateDto postBikeCreateDto) {
        Optional<String> exceptionCheck = bikeCreateAndDeleteRepository.bikeCrete(postBikeCreateDto);
        if(exceptionCheck.get() == "bike_id 중복") {
            throw new BikeCreateAndDeleteException("BIKE ID DUPLICATE", ErrorCode.BIKE_DUPLICATION);
        } else if(exceptionCheck.get() == "존재하지 않는 location_id") {
            throw new BikeCreateAndDeleteException("NON EXIST LOCATION ID", ErrorCode.NON_EXIST_LOCATION);
        }
    }

    public void bikeDelete(PostBikeDeleteDto postBikeDeleteDto) {
        int deleteCheck = bikeCreateAndDeleteRepository.bikeDelete(postBikeDeleteDto);
        if(deleteCheck == 0) {
            throw new BikeCreateAndDeleteException("NON EXIST BIKE ID", ErrorCode.NON_EXIST_BIKE);
        }
    }
}
