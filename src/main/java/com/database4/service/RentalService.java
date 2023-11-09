package com.database4.service;

import com.database4.dto.PostRentalRentDto;
import com.database4.repository.RentalRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public boolean Rent(PostRentalRentDto rentalRentDto){
        return rentalRepository.Rent(rentalRentDto);
    }
}
