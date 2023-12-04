package dp.project.service;

import dp.project.dto.PostRentalRentDto;
import dp.project.dto.PostRentalReturnDto;
import dp.project.dto.ReturnPostRentalReturnDto;
import dp.project.exceptions.RentalRentException;
import dp.project.exceptions.RentalReturnException;
import dp.project.repository.RentalRepository;
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
    public String rentalRent(PostRentalRentDto rentalRentDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        rentalRepository.checkOverfee(user_id).orElseThrow(() -> new RentalRentException("미납금이 존재해 대여에 실패했습니다."));

        rentalRepository.insertRental(rentalRentDto, user_id).orElseThrow(() -> new RentalRentException("보유중인 이용권이 없습니다."));
        rentalRepository.updateBikeStatus(rentalRentDto.getBike_id()).orElseThrow(() -> new RentalRentException("자전거 상태 업데이트 실패"));

        return "대여에 성공했습니다.";

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
        throw new RentalReturnException("반납에 실패했습니다.");
    }
}
