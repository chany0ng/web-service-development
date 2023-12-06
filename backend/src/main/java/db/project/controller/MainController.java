package db.project.controller;

import db.project.dto.ReturnGetAdminMainDto;
import db.project.dto.ReturnGetUserMainDto;
import db.project.service.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RestController
public class MainController {  // 메인페이지 Controller
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/user/main")
    @ResponseBody
    @Operation(
            summary = "사용자 메인화면",
            description = "사용자가 로그인에 성공해서 메인화면으로 넘어갈 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 메인화면 성공")
    })
    // 사용자 메인페이지
    public ResponseEntity<ReturnGetUserMainDto> getUserMain() {
        ReturnGetUserMainDto returnGetUserMainDto = mainService.userMain();

        return ResponseEntity.ok(returnGetUserMainDto);
    }

    @GetMapping("/admin/main")
    @ResponseBody
    @Operation(
            summary = "관리자 메인화면",
            description = "관리자가 로그인에 성공해서 메인화면으로 넘어갈 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관리자 메인화면 성공")
    })
    // 관리자 메인페이지
    public ResponseEntity<ReturnGetAdminMainDto> getAdminMain() {
        ReturnGetAdminMainDto returnGetAdminMainDto = mainService.adminMain();

        return ResponseEntity.ok(returnGetAdminMainDto);
    }
}
