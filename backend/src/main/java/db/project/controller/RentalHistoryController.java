package db.project.controller;

import db.project.service.RentalHistoryService;
import db.project.dto.PostRentalHistoryDto;
import db.project.dto.RentalHistoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class RentalHistoryController {  // 대여/반납 내역 조회 Controller
    private final RentalHistoryService rentalHistoryService;

    public RentalHistoryController(RentalHistoryService rentalHistoryService) {
        this.rentalHistoryService = rentalHistoryService;
    }

    @ResponseBody
    @PostMapping("rentalHistory")
    @Operation(
            summary = "대여/반납 이력",
            description = "대여/반납 이력을 겁색했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    // 사용자의 대여/반납 이력 List
    public ResponseEntity<RentalHistoryResponseDto> postRentalHistory(@RequestBody PostRentalHistoryDto form) {
        return ResponseEntity.ok(rentalHistoryService.rentalHistory(form));
    }
}
