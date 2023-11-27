package database4.controller;

import database4.dto.LocationInfoResponseDto;
import database4.dto.LocationListResponseDto;
import database4.dto.PostMapLocationInfoDto;
import database4.service.MapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
            @ApiResponse(responseCode = "200", description = "대여소 조회 성공"),
            @ApiResponse(responseCode = "204", description = "존재하는 대여소가 없음")
    })
    // 대여소 위치와 각 대여소의 자전거 개수 List
    public ResponseEntity<LocationListResponseDto> getLocationList(){
        LocationListResponseDto locationListResponseDto = mapService.locationList();

        if(locationListResponseDto.getLocations().isEmpty()){
            return ResponseEntity.noContent().build();
        } else{
            return ResponseEntity.ok(locationListResponseDto);
        }
    }

    @PostMapping("/map/info")
    @ResponseBody
    @Operation(
            summary = "대여소 상세 정보",
            description = "특정 대여소를 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대여소 상세 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 값이 전달되어 대여소 상세 정보 보기 실패")
    })
    // 대여소 상세 정보
    public ResponseEntity<LocationInfoResponseDto> postLocationInfo(@RequestBody PostMapLocationInfoDto form) {
        Optional<LocationInfoResponseDto> locationInfoResponseDtoOptional = mapService.locationInfo(form);

        return locationInfoResponseDtoOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
