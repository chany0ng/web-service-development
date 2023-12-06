package db.project.controller;

import db.project.config.jwt.TokenProvider;
import db.project.dto.*;
import db.project.exceptions.ErrorResponse;
import db.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok("{}");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userService.login(userLoginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        userService.logout();
        return ResponseEntity.ok("{}");
    }

    @PostMapping("/auth/findPW")
    public ResponseEntity<String> findPW(@RequestBody FindPWRequest findPWRequest) {
        userService.findPW(findPWRequest);
        return ResponseEntity.ok("{}");
    }
    @PostMapping("/auth/findPW-update")
    public ResponseEntity<String> updatePW(@RequestBody UpdatePWRequest updatePWRequest) {
        try {
            userService.updatePW(updatePWRequest);
            return ResponseEntity.ok("{}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{ \"message\" : \"서버 오류\" }");
        }
    }
    @PostMapping("/update/myInfo")
    public ResponseEntity<String> updateMyInfo(@RequestBody UpdateMyInfoRequest updateMyInfoRequest) {
        try{
            userService.updateUser(updateMyInfoRequest);
            return ResponseEntity.ok().body("{}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{ \"message\" : \"서버 오류\" }");
        }
    }



    @GetMapping("/check")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok().build();
    }


//    @GetMapping("/test")
//    public void test() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            List<String> authorities = authentication.getAuthorities()
//                    .stream()
//                    .map(grantedAuthority -> grantedAuthority.getAuthority())
//                    .collect(Collectors.toList());
//            System.out.println("Principal: " + principal.toString());
//            System.out.println("username: " + authentication.getName());
//            System.out.println("Authorities: " + authorities);
//        } else {
//            System.out.println("인증된 사용자 없음");
//        }
//    }


}
