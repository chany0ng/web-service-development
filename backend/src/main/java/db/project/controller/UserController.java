package db.project.controller;

import db.project.config.jwt.TokenProvider;
import db.project.domain.User;
import db.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        return userService.save(user);
    }

}
