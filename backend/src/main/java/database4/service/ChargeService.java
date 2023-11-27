package database4.service;

import database4.dto.PostChargeDto;
import database4.repository.ChargeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {
    private final ChargeRepository chargeRepository;

    public ChargeService(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    public boolean charge(PostChargeDto postChargeDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        return chargeRepository.charge(postChargeDto, user_id);
    }
}
