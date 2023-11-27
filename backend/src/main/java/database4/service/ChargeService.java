package database4.service;

import database4.dto.PostChargeDto;
import database4.repository.ChargeRepository;
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
