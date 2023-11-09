package com.database4.controller;

import com.database4.dto.PostTicketPurchaseDto;
import com.database4.dto.ReturnGetTicketInfoDto;
import com.database4.dto.TicketListResponseDto;
import com.database4.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TicketController {  // 사용자 이용권 구매 Controller
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @ResponseBody
    @GetMapping("/ticket")
    @Operation(
            summary = "이용권 LIST",
            description = "이용권 목록을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용권 목록 출력 성공")
    })
    // 이용권 종류 List
    public ResponseEntity<TicketListResponseDto> getTicketList(){
        TicketListResponseDto ticketListResponseDto = ticketService.ticketList();

        return ResponseEntity.ok(ticketListResponseDto);
    }


    @ResponseBody
    @PostMapping("/ticket")
    @Operation(
            summary = "이용권 구매",
            description = "이용권 구매를 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용권 구매 성공"),
            @ApiResponse(responseCode = "409", description = "기존 이용권 보유중", content = @Content),
            @ApiResponse(responseCode = "402", description = "소지금 부족", content = @Content)
    })
    // 이용권 구매
    public ResponseEntity<Void> postPurchase(@RequestBody PostTicketPurchaseDto form){
        int rowsAffected = ticketService.purchase(form);
        if(rowsAffected == 1){
            // 이용권을 성공적으로 구매
            return ResponseEntity.ok().build();
        } else if(rowsAffected == 0){
            // 해당 사용자가 이미 이용권을 소유하고 있음
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else{
            // 해당 사용자의 소지금이 이용권을 사기에 부족함
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }
    }
}
