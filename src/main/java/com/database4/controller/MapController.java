package com.database4.controller;

import com.database4.dto.LocationInfoResponseDto;
import com.database4.dto.LocationListResponseDto;
import com.database4.dto.PostMapLocationInfoDto;
import com.database4.service.MapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MapController {  // 지도 Controller
    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/map")
    @ResponseBody
    public ResponseEntity<LocationListResponseDto> getLocationList(){
        // 대여소 위치와 각 대여소의 자전거 개수 List
        LocationListResponseDto locationListResponseDto = mapService.locationList();
        return ResponseEntity.ok(locationListResponseDto);
    }

    @PostMapping("/map/info")
    @ResponseBody
    public ResponseEntity<LocationInfoResponseDto> postLocationInfo(@RequestBody PostMapLocationInfoDto form) {
        LocationInfoResponseDto locationInfoResponseDto = mapService.locationInfo(form);
        return ResponseEntity.ok(locationInfoResponseDto);
    }
}
