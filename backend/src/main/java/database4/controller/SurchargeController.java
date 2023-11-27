package database4.controller;

import database4.dto.PostSurchargeInfoDto;
import database4.dto.PostSurchargePayDto;
import database4.dto.ReturnGetSurchargeOverfeeInfoDto;
import database4.service.SurchargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SurchargeController { // 추가요금 결제 Controller
    private final SurchargeService surchargeService;

    public SurchargeController(SurchargeService surchargeService) {
        this.surchargeService = surchargeService;
    }

    @ResponseBody
    @PostMapping("surcharge/info")
    @Operation(
            summary = "추가요금 정보",
            description = "결제관리에서 추가요금 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가요금 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 값이 전달되어 조회 실패")
    })
    // 추가요금 금액 확인
    public ResponseEntity<ReturnGetSurchargeOverfeeInfoDto> postSurchargeInfo(@RequestBody PostSurchargeInfoDto form){
        Optional<ReturnGetSurchargeOverfeeInfoDto> overfee = surchargeService.overfeeInfo(form);

        return overfee
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @ResponseBody
    @PostMapping("surcharge/pay")
    @Operation(
            summary = "추가요금 지불",
            description = "추가요금 입력 후 결제 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가요금 지불 성공"),
            @ApiResponse(responseCode = "400", description = "입력받은 금액이 추가요금 보다 많아 추가요금 지불 실패")
    })
    // 추가요금 지불
    public ResponseEntity<Void> postSurchargePay(@RequestBody PostSurchargePayDto form){
        Boolean checkOverfee = surchargeService.overfeePay(form);
        if(checkOverfee){
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}
