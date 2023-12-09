package db.project.controller;

import db.project.dto.ReturnGetMainDto;
import db.project.service.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RestController
public class MainController {
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/main")
    @ResponseBody
    @Operation(
            summary = "메인화면",
            description = "로그인에 성공해서 메인화면으로 넘어갈 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메인화면 성공")
    })
    public ResponseEntity<ReturnGetMainDto> getMain() {
        ReturnGetMainDto returnGetMainDto = mainService.findUserInfoNeedForMain();

        return ResponseEntity.ok(returnGetMainDto);
    }
}
