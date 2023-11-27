package database4.service;

import database4.dto.PostSurchargeInfoDto;
import database4.dto.PostSurchargePayDto;
import database4.dto.ReturnGetSurchargeOverfeeInfoDto;
import database4.repository.SurchargeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SurchargeService {
    private final SurchargeRepository surchargeRepository;

    public SurchargeService(SurchargeRepository surchargeRepository) {
        this.surchargeRepository = surchargeRepository;
    }

    public Optional<ReturnGetSurchargeOverfeeInfoDto> overfeeInfo(PostSurchargeInfoDto postSurchargeInfoDto){
        return surchargeRepository.overfeeInfo(postSurchargeInfoDto);
    }

    @Transactional
    public boolean overfeePay(PostSurchargePayDto postSurchargePayDto){
        int overfee = surchargeRepository.getOverfee(postSurchargePayDto.getUser_id());

        if (postSurchargePayDto.getCash() > overfee) {
            return false;
        }

        return surchargeRepository.overfeePay(postSurchargePayDto);
    }
}
