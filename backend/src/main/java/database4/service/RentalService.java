package database4.service;

import database4.dto.PostRentalRentDto;
import database4.dto.PostRentalReturnDto;
import database4.dto.ReturnPostRentalReturnDto;
import database4.exceptions.RentalRentException;
import database4.exceptions.RentalReturnException;
import database4.repository.RentalRepository;
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
    public String rentalRent(PostRentalRentDto rentalRentDto) {
        rentalRepository.checkOverfee(rentalRentDto.getUser_id()).orElseThrow(() -> new RentalRentException("미납금이 존재해 대여에 실패했습니다."));

        rentalRepository.insertRental(rentalRentDto).orElseThrow(() -> new RentalRentException("보유중인 이용권이 없습니다."));
        rentalRepository.updateBikeStatus(rentalRentDto.getBike_id()).orElseThrow(() -> new RentalRentException("자전거 상태 업데이트 실패"));

        return "대여에 성공했습니다.";

    }

    @Transactional
    public ReturnPostRentalReturnDto rentalReturn(PostRentalReturnDto rentalReturnDto){
        int checkRentalUpdate = rentalRepository.updateRental(rentalReturnDto);

        if (checkRentalUpdate > 0) {
            Map<String, Object> value = rentalRepository.getRentalValues(rentalReturnDto);
            int price = (int) value.get("price");  // 이용권 요금
            int cash = (int) value.get("cash");  // 소지금
            int fee = (int) value.get("fee");  // 대여 금액

            int overfee = (fee > cash + price) ? (fee - price) - cash : 0;  // 추가 납부 금액
            int remainCash = (price >= fee) ? cash : ((overfee > 0) ? 0 : cash - (fee - price));

            if (fee > price) {

                if (overfee > 0) {
                    int updatedRows = rentalRepository.updateUserData(rentalReturnDto.getUser_id(), overfee, remainCash);

                    if (updatedRows > 0) {
                        return new ReturnPostRentalReturnDto(fee, cash, overfee);
                    }
                } else {
                    int updatedRows = rentalRepository.updateUserData(rentalReturnDto.getUser_id(), overfee, remainCash);

                    if (updatedRows > 0) {
                        return new ReturnPostRentalReturnDto(fee, fee - price, overfee);
                    }
                }
            } else {
                int updatedRows = rentalRepository.updateUserData(rentalReturnDto.getUser_id(), overfee, remainCash);

                if (updatedRows > 0) {
                    return new ReturnPostRentalReturnDto(fee, 0, overfee);
                }
            }
        }
        throw new RentalReturnException("반납에 실패했습니다.");
    }
}
