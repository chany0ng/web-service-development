package com.database4.controller;

import com.database4.dto.PostTicketPurchaseDto;
import com.database4.dto.TicketListResponseDto;
import com.database4.exceptions.TicketPurchaseException;
import com.database4.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(responseCode = "200", description = "이용권 목록 출력 성공"),
            @ApiResponse(responseCode = "204", description = "존재하는 이용권 목록이 없음")
    })
    // 이용권 종류 List
    public ResponseEntity<TicketListResponseDto> getTicketList(){
        TicketListResponseDto ticketListResponseDto = ticketService.ticketList();

        if(ticketListResponseDto.getTickets().isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(ticketListResponseDto);
        }
    }

    @ResponseBody
    @PostMapping("/ticket")
    @Operation(
            summary = "이용권 구매",
            description = "이용권 구매를 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용권 구매 성공", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "이용권 구매에 성공했습니다.")})
            }),
            @ApiResponse(responseCode = "400", description = "이용권 구매 실패", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "이미 이용권을 보유 중입니다.", name = "TicketOwned"),
                            @ExampleObject(value = "잘못된 시간 정보가 전달되었습니다.", name = "InvalidHour"),
                            @ExampleObject(value = "소지금이 부족합니다.", name = "InsufficientFunds")
                    })
            })
    })
    // 이용권 구매
    public ResponseEntity<String> postPurchase(@RequestBody PostTicketPurchaseDto form){
        try {
            String result = ticketService.purchase(form);
            return ResponseEntity.ok(result);
        } catch (TicketPurchaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
