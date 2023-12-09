package db.project.controller;

import db.project.dto.*;
import db.project.exceptions.ErrorResponse;
import db.project.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class LocationController {  // 대여소 추가 및 삭제 Controller
    private final LocationService locationService;

    @GetMapping({"location/list/{page}", "location/list"})
    @ResponseBody
    @Operation(
            summary = "대여소 리스트",
            description = "대여소 추가/삭제 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대여소 리스트 열람 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 대여소 List
    public ResponseEntity<LocationListResponseDto> getLocationList(@PathVariable(required = false) Optional<Integer> page) {
        LocationListResponseDto locationListResponseDto = locationService.locationList(page);

        return ResponseEntity.ok(locationListResponseDto);
    }

    @PostMapping("location/create")
    @ResponseBody
    @Operation(
            summary = "대여소 추가",
            description = "대여소 id와 주소를 입력하고 추가 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대여소 추가 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            }),
            @ApiResponse(responseCode = "409", description = "중복된 대여소 ID", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 대여소 생성
    public ResponseEntity<String> postLocationCreate(@RequestBody PostLocationCreateDto form) {
        locationService.locationCreate(form);

        return ResponseEntity.ok("{}");
    }

    @PostMapping("location/delete")
    @ResponseBody
    @Operation(
            summary = "대여소 삭제",
            description = "대여소 삭제 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대여소 삭제 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            }),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 대여소 ID", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    //대여소 삭제
    public ResponseEntity<String> postLocationDelete(@RequestBody PostLocationDeleteDto form) {
        locationService.locationDelete(form);

        return ResponseEntity.ok("{}");
    }
}
