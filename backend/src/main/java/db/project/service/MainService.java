package db.project.service;

import db.project.dto.ReturnGetAdminMainDto;
import db.project.dto.ReturnGetUserMainDto;
import db.project.repository.MainRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    private final MainRepository mainRepository;

    public MainService(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public ReturnGetUserMainDto userMain() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        ReturnGetUserMainDto returnGetUserMainDto = mainRepository.userMain(user_id);

        return returnGetUserMainDto;
    }

    public ReturnGetAdminMainDto adminMain() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        ReturnGetAdminMainDto returnGetAdminMainDto = mainRepository.adminMain(user_id);

        return returnGetAdminMainDto;
    }
}
