package db.project.service;

import db.project.dto.ReturnGetMainDto;
import db.project.repository.MainRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    private final MainRepository mainRepository;

    public MainService(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public ReturnGetMainDto findUserInfoNeedForMain() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        ReturnGetMainDto returnGetMainDto = mainRepository.findUserInfoNeedForMain(user_id);

        return returnGetMainDto;
    }
}
