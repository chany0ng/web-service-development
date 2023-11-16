package com.database4.service;

import com.database4.dto.PostChargeDto;
import com.database4.repository.ChargeRepository;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {
    private final ChargeRepository chargeRepository;

    public ChargeService(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    public boolean charge(PostChargeDto postChargeDto) {

        return chargeRepository.charge(postChargeDto);
    }
}
