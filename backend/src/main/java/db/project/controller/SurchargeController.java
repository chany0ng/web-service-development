package db.project.controller;

import db.project.service.SurchargeService;
import db.project.dto.ReturnGetSurchargeOverfeeInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
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
            @ApiResponse(responseCode = "200", description = "추가요금 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 값이 전달되어 조회 실패")
    })
    // 추가요금 금액 확인
    public ResponseEntity<ReturnGetSurchargeOverfeeInfoDto> postSurchargeInfo(){
        Optional<ReturnGetSurchargeOverfeeInfoDto> overfee = surchargeService.overfeeInfo();

        return overfee
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @ResponseBody
    @GetMapping("surcharge/pay")
    @Operation(
            summary = "추가요금 지불",
            description = "추가요금 입력 후 결제 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가요금 지불 성공"),
            @ApiResponse(responseCode = "400", description = "소지금이 추가요금보다 적어 추가요금 지불 실패")
    })
    // 추가요금 지불
    public ResponseEntity<Void> postSurchargePay(){
        Boolean checkOverfee = surchargeService.overfeePay();
        if(checkOverfee){
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}
