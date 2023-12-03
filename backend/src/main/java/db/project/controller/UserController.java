package db.project.controller;

import db.project.config.jwt.TokenProvider;
import db.project.domain.*;
import db.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class UserController {
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok("{}");
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userService.login(userLoginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        userService.logout();
        return ResponseEntity.ok("{}");
    }

    @PostMapping("/findPW-question")
    public ResponseEntity<PWQuestionResponse> findPWQuestion(@RequestBody PWQuestionRequest pwQuestionRequest) {
        return ResponseEntity.ok(userService.findPWQuestion(pwQuestionRequest));
    }

    @PostMapping("/findPW-verification")
    public ResponseEntity<String> checkPWAnswer(@RequestBody CheckAnswerRequest checkAnswerRequest) {
        userService.checkPWAnswer(checkAnswerRequest);
        return ResponseEntity.ok("{}");
    }

    @PostMapping("/findPW-reset")
    public ResponseEntity<String> updatePW(@RequestBody UpdatePWRequest updatePWRequest) {
        try {
            userService.updatePW(updatePWRequest);
            return ResponseEntity.ok("{}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{ \"message\" : \"서버 오류\" }");
        }
    }


    @GetMapping("/check")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok().build();
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
