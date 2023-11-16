package com.database4.service;

import com.database4.dto.PostSurchargeInfoDto;
import com.database4.dto.PostSurchargePayDto;
import com.database4.repository.SurchargeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SurchargeService {
    private final SurchargeRepository surchargeRepository;

    public SurchargeService(SurchargeRepository surchargeRepository) {
        this.surchargeRepository = surchargeRepository;
    }

    public Optional<Integer> overfeeInfo(PostSurchargeInfoDto postSurchargeInfoDto){
        return surchargeRepository.overfeeInfo(postSurchargeInfoDto);
    }

    public boolean overfeePay(PostSurchargePayDto postSurchargePayDto){
        return surchargeRepository.overfeePay(postSurchargePayDto);
    }
}
