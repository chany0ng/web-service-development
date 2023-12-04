package dp.project.service;

import dp.project.dto.ReturnGetSurchargeOverfeeInfoDto;
import dp.project.repository.SurchargeRepository;
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

    public Optional<ReturnGetSurchargeOverfeeInfoDto> overfeeInfo(){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        return surchargeRepository.overfeeInfo(user_id);
    }

    @Transactional
    public boolean overfeePay(){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, Object> overfeeAndCash = surchargeRepository.getOverfeeAndCash(user_id);

        int overfee = (int) overfeeAndCash.get("overfee");
        int cash = (int) overfeeAndCash.get("cash");

        if (cash < overfee) {
            return false;
        }
        return surchargeRepository.overfeePay(overfee, user_id);
    }
}
