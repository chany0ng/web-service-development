package db.project.service;

import db.project.dto.PostRentalHistoryDto;
import db.project.dto.RentalHistoryResponseDto;
import db.project.dto.ReturnPostRentalHistoryDto;
import db.project.repository.RentalRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalHistoryService {
    private final RentalRepository rentalRepository;

    public RentalHistoryService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public RentalHistoryResponseDto rentalHistory(PostRentalHistoryDto postRentalHistoryDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<ReturnPostRentalHistoryDto> rentalHistoryList = rentalRepository.findRentalHistory(postRentalHistoryDto, user_id);
        RentalHistoryResponseDto response = new RentalHistoryResponseDto();
        for(ReturnPostRentalHistoryDto rentalHistory : rentalHistoryList) {
            response.getRentalInfo().add(rentalHistory);
        }

        return response;
    }
}
