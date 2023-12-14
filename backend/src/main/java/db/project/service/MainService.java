package db.project.service;

import db.project.dto.ReturnGetAdminMainDto;
import db.project.dto.ReturnGetUserMainDto;
import db.project.repository.ReportRepository;
import db.project.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public MainService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public ReturnGetUserMainDto userMain() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        ReturnGetUserMainDto returnGetUserMainDto = userRepository.userMain(user_id);

        return returnGetUserMainDto;
    }

    public ReturnGetAdminMainDto adminMain() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        ReturnGetAdminMainDto returnGetAdminMainDto = reportRepository.adminMain(user_id);

        return returnGetAdminMainDto;
    }
}
