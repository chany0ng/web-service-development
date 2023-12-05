package db.project.controller;

import db.project.exceptions.ErrorResponse;
import db.project.service.RentalService;
import db.project.dto.PostRentalRentDto;
import db.project.dto.PostRentalReturnDto;
import db.project.dto.ReturnPostRentalReturnDto;
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
public class RentalController {  // 자전거 대여, 반납 Controller
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @ResponseBody
    @PostMapping("rent")
    @Operation(
            summary = "자전거 대여",
            description = "원하는 자전거의 대여 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대여 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            }),
            @ApiResponse(responseCode = "400", description = "이용권 미보유", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "402", description = "미납금 존재", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 자전거 대여
    public ResponseEntity<String> postRent(@RequestBody PostRentalRentDto form){
        rentalService.rentalRent(form);

        return ResponseEntity.ok("{}");
    }

    @ResponseBody
    @PostMapping("return")
    @Operation(
            summary = "자전거 반납",
            description = "대여소에서 자전거의 반납 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "반납 성공"),
            @ApiResponse(responseCode = "400", description = "반납 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ReturnPostRentalReturnDto> postReturn(@RequestBody PostRentalReturnDto form){
        ReturnPostRentalReturnDto postRentalReturnDto = rentalService.rentalReturn(form);

        return ResponseEntity.ok(postRentalReturnDto);
    }
}
