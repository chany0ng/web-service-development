package database4.controller;

import database4.dto.PostChargeDto;
import database4.service.ChargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChargeController {  // 사용자 금액 충전 Controller

    private final ChargeService chargeService;

    public ChargeController(ChargeService chargeService){
        this.chargeService = chargeService;
    }

    @PostMapping("charge")
    @ResponseBody
    @Operation(
            summary = "금액 충전",
            description = "충전할 금액을 입력하고 충전 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "금액 충전 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 값이 전달되어 충전 실패")
    })
    // 사용자 금액 충전
    public ResponseEntity<Void> postCharge(@RequestBody PostChargeDto form){
        boolean checkUpdate = chargeService.charge(form);
        if(checkUpdate){
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
