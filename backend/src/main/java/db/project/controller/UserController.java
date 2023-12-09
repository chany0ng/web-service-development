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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @PostMapping("/auth/findPW/update")
    public ResponseEntity<String> updatePW(@RequestBody UpdatePWRequest updatePWRequest) {
        userService.updatePW(updatePWRequest);
        return ResponseEntity.ok("{}");
    }
    @PostMapping("/myInfo/update")
    public ResponseEntity<String> updateMyInfo(@RequestBody UpdateMyInfoRequest updateMyInfoRequest) {
        userService.updateUser(updateMyInfoRequest);
        return ResponseEntity.ok().body("{}");
    }

    @GetMapping({"admin/user/info/list/{page}", "admin/user/info/list"})
    @Operation(
            summary = "회원관리 리스트",
            description = "회원관리 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원관리 열람 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 회원정보 리스트
    public ResponseEntity<UserInfoListResponseDto> userInfoList(@PathVariable(required = false) Optional<Integer> page) {
        return ResponseEntity.ok(userService.selectUserInfoList(page));
    }

    @PostMapping({"admin/user/info/list/{page}", "admin/user/info/list"})
    @Operation(
            summary = "회원관리 리스트 ID 조회",
            description = "찾고자 하는 회원의 ID를 입력 후 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ID로 회원정보 찾기 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // ID를 이용한 회원정보 리스트
    public ResponseEntity<UserInfoListResponseDto> userInfoListById(@PathVariable(required = false) Optional<Integer> page, @RequestBody PostUserInfoListDto form) {
        return ResponseEntity.ok(userService.selectUserInfoListById(page, form));
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
