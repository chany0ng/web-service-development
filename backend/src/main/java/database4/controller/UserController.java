package database4.controller;

import database4.dto.PostUserLoginDto;
import database4.dto.PostUserJoinDto;
import database4.dto.ReturnPostUserLoginDto;
import database4.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {  // 사용자 로그인 및 회원가입 Controller
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseBody
    @Operation(
            summary = "사용자 로그인",
            description = "아이디 비밀번호 입력 후 로그인 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "id 또는 비밀번호가 일치하지 않아 로그인 실패", content = @Content)
    })
    // 사용자 로그인
    public ResponseEntity<ReturnPostUserLoginDto> postLogin(@RequestBody PostUserLoginDto form){

        ReturnPostUserLoginDto returnPostUserLoginDto = userService.login(form);
        if(returnPostUserLoginDto != null){ // 로그인 성공
            return ResponseEntity.ok(returnPostUserLoginDto);
        } else{  // 해당 id와 비밀번호가 일치하는 사용자가 없음
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @ResponseBody
    @PostMapping("join")
    @Operation(
            summary = "회원가입",
            description = "개인 정보를 입력 후 회원가입 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공")
    })
    // 사용자 회원가입
    public ResponseEntity<Void> postJoin(@RequestBody PostUserJoinDto form){
        userService.join(form);
        return ResponseEntity.ok().build();
    }
}
