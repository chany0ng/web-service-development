package db.project.service;

import db.project.dto.PostChargeDto;
import db.project.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {
    private final UserRepository userRepository;

    public ChargeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void charge(PostChargeDto postChargeDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        userRepository.updateCashById(user_id, postChargeDto.getCash());
    }
}
