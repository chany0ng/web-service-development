package db.project.controller;

import db.project.dto.LocationInfoResponseDto;
import db.project.dto.LocationListResponseDto;
import db.project.dto.PostMapLocationInfoDto;
import db.project.exceptions.ErrorResponse;
import db.project.service.MapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MapController {  // 지도 Controller
    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/map")
    @ResponseBody
    @Operation(
            summary = "대여소 LIST",
            description = "대여소 조회 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대여소 조회 성공")
    })
    // 대여소 위치와 각 대여소의 자전거 개수 List
    public ResponseEntity<LocationListResponseDto> getLocationList(){
        LocationListResponseDto locationListResponseDto = mapService.locationList();

        return ResponseEntity.ok(locationListResponseDto);
    }

    @PostMapping("/map/info")
    @ResponseBody
    @Operation(
            summary = "대여소 상세 정보",
            description = "특정 대여소를 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대여소 상세 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 값이 전달되어 대여소 상세 정보 보기 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 대여소 상세 정보
    public ResponseEntity<LocationInfoResponseDto> postLocationInfo(@RequestBody PostMapLocationInfoDto form) {
        Optional<LocationInfoResponseDto> locationInfoResponseDtoOptional = mapService.locationInfo(form);

        return ResponseEntity.ok(locationInfoResponseDtoOptional.get());
    }
}
