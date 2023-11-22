package com.database4.controller;

import com.database4.dto.PostRentalRentDto;
import com.database4.dto.PostRentalReturnDto;
import com.database4.dto.ReturnPostRentalReturnDto;
import com.database4.exceptions.RentalRentException;
import com.database4.exceptions.RentalReturnException;
import com.database4.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "대여에 성공했습니다.")})
            }),
            @ApiResponse(responseCode = "400", description = "대여 실패", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "미납금이 존재해 대여에 실패했습니다.", name = "OverfeeOwned"),
                            @ExampleObject(value = "보유중인 이용권이 없습니다.", name = "InvalidTicket"),
                            @ExampleObject(value = "자전거 상태 업데이트 실패", name = "BikeStatusUpdateFailed")
                    })
            })
    })
    // 자전거 대여
    public ResponseEntity<String> postRent(@RequestBody PostRentalRentDto form){
        try {
            String result = rentalService.rentalRent(form);
            return ResponseEntity.ok(result);
        } catch (RentalRentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("return")
    @Operation(
            summary = "자전거 반납",
            description = "대여소에서 자전거의 반납 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "반납 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 값이 전달되어 반납 실패")
    })
    public ResponseEntity<ReturnPostRentalReturnDto> postReturn(@RequestBody PostRentalReturnDto form){
        try {
            ReturnPostRentalReturnDto postRentalReturnDto = rentalService.rentalReturn(form);
            return ResponseEntity.ok(postRentalReturnDto);
        } catch (RentalReturnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
