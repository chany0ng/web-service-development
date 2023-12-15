package db.project.controller;

import db.project.exceptions.ErrorResponse;
import db.project.service.SurchargeService;
import db.project.dto.ReturnGetSurchargeOverfeeInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class SurchargeController { // 추가요금 결제 Controller
    private final SurchargeService surchargeService;

    public SurchargeController(SurchargeService surchargeService) {
        this.surchargeService = surchargeService;
    }

    @ResponseBody
    @GetMapping("surcharge/info")
    @Operation(
            summary = "추가요금 정보",
            description = "결제관리에서 추가요금 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가요금 조회 성공")
    })
    // 추가요금 금액 확인
    public ResponseEntity<ReturnGetSurchargeOverfeeInfoDto> postSurchargeInfo(){
        ReturnGetSurchargeOverfeeInfoDto overfee = surchargeService.overfeeInfo();

        return ResponseEntity.ok(overfee);
    }

    @ResponseBody
    @GetMapping("surcharge/pay")
    @Operation(
            summary = "추가요금 지불",
            description = "추가요금 입력 후 결제 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가요금 지불 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            }),
            @ApiResponse(responseCode = "402", description = "소지금 부족", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 추가요금 지불
    public ResponseEntity<String> postSurchargePay(){
        surchargeService.overfeePay();
        return ResponseEntity.ok("{}");

    }
}
