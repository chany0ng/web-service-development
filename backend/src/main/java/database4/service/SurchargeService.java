package database4.service;

import database4.dto.PostSurchargePayDto;
import database4.dto.ReturnGetSurchargeOverfeeInfoDto;
import database4.repository.SurchargeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SurchargeService {
    private final SurchargeRepository surchargeRepository;

    public SurchargeService(SurchargeRepository surchargeRepository) {
        this.surchargeRepository = surchargeRepository;
    }

    public Optional<ReturnGetSurchargeOverfeeInfoDto> overfeeInfo(){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        return surchargeRepository.overfeeInfo(user_id);
    }

    @Transactional
    public boolean overfeePay(PostSurchargePayDto postSurchargePayDto){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        int overfee = surchargeRepository.getOverfee(user_id);

        if (postSurchargePayDto.getCash() > overfee) {
            return false;
        }

        return surchargeRepository.overfeePay(postSurchargePayDto.getCash(), user_id);
    }
}
