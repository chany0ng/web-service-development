package db.project.service;

import db.project.dto.ReturnGetSurchargeOverfeeInfoDto;
import db.project.exceptions.ErrorCode;
import db.project.exceptions.SurchargeException;
import db.project.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class SurchargeService {
    private final UserRepository userRepository;

    public SurchargeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ReturnGetSurchargeOverfeeInfoDto overfeeInfo(){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        int overfee = userRepository.findOverfeeById(user_id);
        ReturnGetSurchargeOverfeeInfoDto returnGetSurchargeOverfeeInfoDto = new ReturnGetSurchargeOverfeeInfoDto(overfee);

        return returnGetSurchargeOverfeeInfoDto;
    }

    @Transactional
    public void overfeePay(){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, Object> overfeeAndCash = userRepository.findOverfeeAndCashById(user_id);

        int overfee = (int) overfeeAndCash.get("overfee");
        int cash = (int) overfeeAndCash.get("cash");

        if (cash < overfee) {
            throw new SurchargeException("NOT ENOUGH MONEY", ErrorCode.NOT_ENOUGH_MONEY);
        }

        userRepository.updateCashAndOverfeeById(overfee, user_id);
    }
}
