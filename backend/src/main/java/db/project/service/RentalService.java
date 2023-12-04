package db.project.service;

import db.project.dto.PostRentalRentDto;
import db.project.dto.PostRentalReturnDto;
import db.project.dto.ReturnPostRentalReturnDto;
import db.project.exceptions.ErrorCode;
import db.project.exceptions.RentalException;
import db.project.repository.RentalRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Transactional
    public void rentalRent(PostRentalRentDto rentalRentDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        rentalRepository.checkOverfee(user_id).orElseThrow(() -> new RentalException("EXIST UNPAID AMOUNT", ErrorCode.EXIST_UNPAID_AMOUNT));

        rentalRepository.insertRental(rentalRentDto, user_id).orElseThrow(() -> new RentalException("DON'T HAVE TICKET", ErrorCode.NO_TICKET));

        rentalRepository.updateBikeStatus(rentalRentDto.getBike_id());
    }

    @Transactional
    public ReturnPostRentalReturnDto rentalReturn(PostRentalReturnDto rentalReturnDto){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        int checkRentalUpdate = rentalRepository.updateRental(rentalReturnDto, user_id);

        if (checkRentalUpdate > 0) {
            Map<String, Object> value = rentalRepository.getRentalValues(rentalReturnDto.getBike_id(), user_id);
            int price = (int) value.get("price");  // 이용권 요금
            int cash = (int) value.get("cash");  // 소지금
            int fee = (int) value.get("fee");  // 대여 금액

            int overfee = (fee > cash + price) ? (fee - price) - cash : 0;  // 추가 납부 금액
            int remainCash = (price >= fee) ? cash : ((overfee > 0) ? 0 : cash - (fee - price));

            if (fee > price) {

                if (overfee > 0) {
                    int updatedRows = rentalRepository.updateUserData(user_id, overfee, remainCash);

                    if (updatedRows > 0) {
                        return new ReturnPostRentalReturnDto(fee, cash, overfee);
                    }
                } else {
                    int updatedRows = rentalRepository.updateUserData(user_id, overfee, remainCash);

                    if (updatedRows > 0) {
                        return new ReturnPostRentalReturnDto(fee, fee - price, overfee);
                    }
                }
            } else {
                int updatedRows = rentalRepository.updateUserData(user_id, overfee, remainCash);

                if (updatedRows > 0) {
                    return new ReturnPostRentalReturnDto(fee, 0, overfee);
                }
            }
        }
        throw new RentalException("FAIL BIKE RETURN", ErrorCode.FAIL_BIKE_RETURN);
    }
}
