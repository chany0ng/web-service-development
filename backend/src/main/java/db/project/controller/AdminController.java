package db.project.controller;

import db.project.domain.Admin;
import db.project.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("test")
    public List<Admin> adminList() {
        List<Admin> adminList = adminService.getAdminList();
        return adminList;
    }

    @GetMapping("test/{id}")
    public Admin findAdmin(@PathVariable String id) {
        Admin admin = adminService.findAdminByAdminId(id);
        return admin;
    }
}
