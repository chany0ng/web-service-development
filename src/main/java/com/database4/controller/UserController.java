package com.database4.controller;

import com.database4.dto.PostUserLoginDto;
import com.database4.dto.PostUserJoinDto;
import com.database4.dto.ReturnPostUserLoginDto;
import com.database4.service.UserService;
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
    // 사용자 회원가입
    public ResponseEntity<Void> postJoin(@RequestBody PostUserJoinDto form){
        userService.join(form);
        return ResponseEntity.ok().build();
    }
}
