package db.project.service;

import db.project.domain.Admin;
import db.project.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public List<Admin> getAdminList() {
        return this.adminRepository.findList();
    }

    public Admin findAdminByAdminId(String adminId) {
        return this.adminRepository.findAdminByAdminId(adminId)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + adminId));
    }
}
