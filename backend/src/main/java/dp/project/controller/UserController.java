package dp.project.controller;

import dp.project.config.jwt.TokenProvider;
import dp.project.domain.UserLoginResponse;
import dp.project.domain.User;
import dp.project.domain.UserLoginRequest;
import dp.project.service.UserService;
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
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResponse userLoginResponse = userService.login(userLoginRequest);
        return ResponseEntity.ok(userLoginResponse);
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