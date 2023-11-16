package com.database4.service;

import com.database4.dto.PostRentalRentDto;
import com.database4.dto.PostRentalReturnDto;
import com.database4.dto.ReturnPostRentalReturnDto;
import com.database4.exceptions.RentalRentException;
import com.database4.exceptions.RentalReturnException;
import com.database4.exceptions.TicketPurchaseException;
import com.database4.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public String Rent(PostRentalRentDto rentalRentDto){
        try {
                return rentalRepository.Rent(rentalRentDto)
                    .orElseThrow(() -> new RentalRentException("대여에 실패했습니다."));
        } catch (TicketPurchaseException e) {
            throw e;
        }
    }

    public ReturnPostRentalReturnDto Return(PostRentalReturnDto rentalReturnDto){
        try {
            return rentalRepository.Return(rentalReturnDto)
                    .orElseThrow(() -> new RentalReturnException("반납에 실패했습니다."));
        } catch (RentalReturnException e) {
            throw e;
        }
    }
}
