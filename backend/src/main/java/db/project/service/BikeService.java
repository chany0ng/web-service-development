package db.project.service;

import db.project.dto.*;
import db.project.exceptions.BikeException;
import db.project.exceptions.ErrorCode;
import db.project.repository.BikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeService {
    private final BikeRepository bikeRepository;

    @Transactional
    public BikeListResponseDto bikeList(Optional<Integer> page) {
        int bikePage;
        if(page.isEmpty()) {
            bikePage = 0;
        } else {
            bikePage = (page.get() - 1) * 10;
        }

        Optional<List<ReturnGetBikeListDto>> bikeListOptional = bikeRepository.findBikeByStatus(bikePage);
        if(bikeListOptional.isEmpty()) {
            throw new BikeException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int bikeCount = bikeRepository.findBikeCountByStatus();
        if(bikePage != 0 && bikeCount <= bikePage) {
            throw new BikeException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        List<ReturnGetBikeListDto> bikeList = bikeListOptional.get();
        BikeListResponseDto response = new BikeListResponseDto(bikeCount);
        for (ReturnGetBikeListDto bike : bikeList) {
            response.getBikeList().add(bike);
        }

        return response;
    }

    public void bikeCreate(PostBikeCreateDto postBikeCreateDto) {
        Optional<String> exceptionCheck = bikeRepository.createBike(postBikeCreateDto);
        if(exceptionCheck.get() == "bike_id 중복") {
            throw new BikeException("BIKE ID DUPLICATE", ErrorCode.BIKE_DUPLICATION);
        } else if(exceptionCheck.get() == "존재하지 않는 location_id") {
            throw new BikeException("NON EXIST LOCATION ID", ErrorCode.NON_EXIST_LOCATION);
        }
    }

    public void bikeDelete(PostBikeDeleteDto postBikeDeleteDto) {
        int deleteCheck = bikeRepository.updateStatusDeletedByIdAndStatus(postBikeDeleteDto);
        if(deleteCheck == 0) {
            throw new BikeException("NON EXIST BIKE ID", ErrorCode.NON_EXIST_BIKE);
        }
    }
}
