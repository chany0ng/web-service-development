package com.database4.controller;

import com.database4.dto.PostRentalRentDto;
import com.database4.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
            @ApiResponse(responseCode = "200", description = "대여 성공"),
            @ApiResponse(responseCode = "409", description = "이용권이 없어 대여 실패", content = @Content)
    })
    // 자전거 대여
    public ResponseEntity<Void> postRent(@RequestBody PostRentalRentDto form){
        boolean checkInsert = rentalService.Rent(form);
        if(checkInsert){
            // 대여 성공
            return ResponseEntity.ok().build();
        } else{
            // 이용권이 없어 대여 실패
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
