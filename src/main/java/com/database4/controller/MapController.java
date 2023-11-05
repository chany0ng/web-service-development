package com.database4.controller;

import com.database4.dto.PostGetMapLocationInfoDto;
import com.database4.dto.ReturnGetMapLocationDto;
import com.database4.dto.ReturnGetMapLocationInfoDto;
import com.database4.service.MapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MapController {  // 지도 Controller
    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/map")
    @ResponseBody
    public ResponseEntity<Map<String, List<ReturnGetMapLocationDto>>> getLocationList(){
        // 대여소 위치와 각 대여소의 자전거 개수 List
        List<ReturnGetMapLocationDto> returnGetMapLocationDtoList = mapService.locationList();
        Map<String, List<ReturnGetMapLocationDto>> response = new HashMap<>();
        response.put("locations", returnGetMapLocationDtoList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/map/info")
    @ResponseBody
    public ResponseEntity<ReturnGetMapLocationInfoDto> postLocationInfo(@RequestBody PostGetMapLocationInfoDto form){
        // 해당 대여소 상세정보 확인: 사용가능 여부, 자전거 개수, 대여소 주소, 즐겨찾기 여부
        ReturnGetMapLocationInfoDto returnGetMapLocationInfoDto = mapService.locationInfo(form);
        return ResponseEntity.ok(returnGetMapLocationInfoDto);
    }
}
