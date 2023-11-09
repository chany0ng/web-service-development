package com.database4.controller;

import com.database4.dto.PostChargeDto;
import com.database4.service.ChargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
            @ApiResponse(responseCode = "200", description = "금액 충전 성공")
    })
    // 사용자 금액 충전
    public ResponseEntity<Void> postCharge(@RequestBody PostChargeDto form){
        chargeService.charge(form);
        return ResponseEntity.ok().build();
    }
}
