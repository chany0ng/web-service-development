package db.project.controller;

import db.project.dto.BikeListResponseDto;
import db.project.dto.PostBikeCreateDto;
import db.project.dto.PostBikeDeleteDto;
import db.project.service.BikeCreateAndDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class BikeCreateAndDeleteController {
    private final BikeCreateAndDeleteService bikeCreateAndDeleteService;

    @GetMapping("bike/list/{page}")
    @ResponseBody
    public ResponseEntity<BikeListResponseDto> getBikeList(@PathVariable int page) {
        BikeListResponseDto bikeListResponseDto = bikeCreateAndDeleteService.bikeList(page);

        return ResponseEntity.ok(bikeListResponseDto);
    }

    @PostMapping("bike/create")
    @ResponseBody
    public ResponseEntity<String> postBikeCreate(@RequestBody PostBikeCreateDto form) {
        bikeCreateAndDeleteService.bikeCrete(form);

        return ResponseEntity.ok("{}");
    }

    @PostMapping("bike/delete")
    @ResponseBody
    public ResponseEntity<String> postBikeDelete(@RequestBody PostBikeDeleteDto form) {
        bikeCreateAndDeleteService.bikeDelete(form);

        return ResponseEntity.ok("{}");
    }
}
