package db.project.controller;

import db.project.exceptions.ErrorResponse;
import db.project.service.TicketService;
import db.project.dto.PostTicketGiftDto;
import db.project.dto.PostTicketPurchaseDto;
import db.project.dto.TicketListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
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
            @ApiResponse(responseCode = "200", description = "이용권 구매 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            }),
            @ApiResponse(responseCode = "409", description = "이용권 중복", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "402", description = "소지금 부족", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 이용권 구매
    public ResponseEntity<String> postPurchase(@RequestBody PostTicketPurchaseDto form){
        ticketService.purchase(form);

        return ResponseEntity.ok("{}");
    }

    @ResponseBody
    @PostMapping("/ticket/gift")
    @Operation(
            summary = "이용권 선물",
            description = "이용권 서물을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용권 선물 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            }),
            @ApiResponse(responseCode = "409", description = "받는사람 이용권 중복", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "402", description = "소지금 부족", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 이용권 선물
    public ResponseEntity<String> postGift(@RequestBody PostTicketGiftDto form){
        ticketService.gift(form);

        return ResponseEntity.ok("{}");
    }
}
