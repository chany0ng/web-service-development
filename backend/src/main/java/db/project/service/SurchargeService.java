package db.project.service;

import db.project.dto.ReturnGetSurchargeOverfeeInfoDto;
import db.project.exceptions.ErrorCode;
import db.project.exceptions.SurchargeException;
import db.project.repository.SurchargeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class SurchargeService {
    private final SurchargeRepository surchargeRepository;

    public SurchargeService(SurchargeRepository surchargeRepository) {
        this.surchargeRepository = surchargeRepository;
    }

    public ReturnGetSurchargeOverfeeInfoDto overfeeInfo(){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        return surchargeRepository.overfeeInfo(user_id).get();
    }

    @Transactional
    public void overfeePay(){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, Object> overfeeAndCash = surchargeRepository.getOverfeeAndCash(user_id);

        int overfee = (int) overfeeAndCash.get("overfee");
        int cash = (int) overfeeAndCash.get("cash");

        if (cash < overfee) {
            throw new SurchargeException("NOT ENOUGH MONEY", ErrorCode.NOT_ENOUGH_MONEY);
        }

        surchargeRepository.overfeePay(overfee, user_id);
    }
}
