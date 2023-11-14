package db.project.controller;

import db.project.config.jwt.TokenProvider;
import db.project.domain.Token;
import db.project.domain.User;
import db.project.domain.UserLoginRequest;
import db.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody UserLoginRequest userLoginRequest) {
        Token token = userService.login(userLoginRequest.getUserId(), userLoginRequest.getPassword());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/test")
    public void test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();

            List<String> authorities = authentication.getAuthorities()
                    .stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList());
            System.out.println("Principal: " + principal.toString());
            System.out.println("username: " + authentication.getName());
            System.out.println("Authorities: " + authorities);
        } else {
            System.out.println("인증된 사용자 없음");
        }
    }

}
