package com.database4.service;

import com.database4.dto.PostChargeDto;
import com.database4.repository.ChargeRepository;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {
    private ChargeRepository chargeRepository;

    public ChargeService(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    public void charge(PostChargeDto postChargeDto) {
        chargeRepository.charge(postChargeDto);
    }
}
