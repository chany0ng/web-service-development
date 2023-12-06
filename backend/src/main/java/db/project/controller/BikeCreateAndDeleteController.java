package db.project.controller;

import db.project.service.BikeCreateAndDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class BikeCreateAndDeleteController {
    private final BikeCreateAndDeleteService bikeCreateAndDeleteService;

    @GetMapping("bike/list")
    public void getBikeList() {

    }

    @PostMapping("bike/create")
    public void postBikeCreate() {

    }

    @PostMapping("bike/delete")
    public void postBikeDelete() {

    }
}
