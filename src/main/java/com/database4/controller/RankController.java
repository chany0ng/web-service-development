package com.database4.controller;

import com.database4.service.RankService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RankController {
    private final RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

}
