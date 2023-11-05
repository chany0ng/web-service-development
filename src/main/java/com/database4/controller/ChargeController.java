package com.database4.controller;

import com.database4.dto.PostChargeDto;
import com.database4.service.ChargeService;
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
    // 사용자 금액 충전
    public ResponseEntity<Void> charge(@RequestBody PostChargeDto form){
        chargeService.charge(form);
        return ResponseEntity.ok().build();
    }
}
