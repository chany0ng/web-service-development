package com.database4.service;

import com.database4.dto.*;
import com.database4.repository.RentalHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalHistoryService {
    private final RentalHistoryRepository rentalHistoryRepository;

    public RentalHistoryService(RentalHistoryRepository rentalHistoryRepository) {
        this.rentalHistoryRepository = rentalHistoryRepository;
    }

    public RentalHistoryResponseDto rentalHistory(PostRentalHistoryDto postRentalHistoryDto) {
        List<ReturnPostRentalHistoryDto> rentalHistoryList = rentalHistoryRepository.rentalHistory(postRentalHistoryDto);
        RentalHistoryResponseDto response = new RentalHistoryResponseDto();
        for(ReturnPostRentalHistoryDto rentalHistory : rentalHistoryList) {
            response.getRentalInfo().add(rentalHistory);
        }

        return response;
    }
}
