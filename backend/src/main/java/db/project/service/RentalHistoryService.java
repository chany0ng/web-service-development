package db.project.service;

import db.project.dto.PostRentalHistoryDto;
import db.project.dto.RentalHistoryResponseDto;
import db.project.dto.ReturnPostRentalHistoryDto;
import db.project.repository.RentalHistoryRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalHistoryService {
    private final RentalHistoryRepository rentalHistoryRepository;

    public RentalHistoryService(RentalHistoryRepository rentalHistoryRepository) {
        this.rentalHistoryRepository = rentalHistoryRepository;
    }

    public RentalHistoryResponseDto rentalHistory(PostRentalHistoryDto postRentalHistoryDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<ReturnPostRentalHistoryDto> rentalHistoryList = rentalHistoryRepository.rentalHistory(postRentalHistoryDto, user_id);
        RentalHistoryResponseDto response = new RentalHistoryResponseDto();
        for(ReturnPostRentalHistoryDto rentalHistory : rentalHistoryList) {
            response.getRentalInfo().add(rentalHistory);
        }

        return response;
    }
}
