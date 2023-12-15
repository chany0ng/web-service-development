package db.project.controller;

import db.project.dto.RankDto;
import db.project.service.RankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class RankController {  //rank Controller
    private final RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping("rank/time")
    @ResponseBody
    @Operation(
            summary = "이용시간 랭킹",
            description = "랭킹 조회에서 이용시간에 따른 랭킹 조회 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용시간 랭킹 조회 성공")
    })
    // 이용시간에 따른 랭킹 조회
    public ResponseEntity<RankDto.RankTimeResponse> getRankTime() {
        RankDto.RankTimeResponse rankTimeResponseDto = rankService.timeRank();

        return ResponseEntity.ok(rankTimeResponseDto);
    }

    @GetMapping("rank/count")
    @ResponseBody
    @Operation(
            summary = "이용횟수 랭킹",
            description = "랭킹 조회에서 이용횟수 따른 랭킹 조회 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용횟수 랭킹 조회 성공")
    })
    // 이용회수에 따른 랭킹 조회
    public ResponseEntity<RankDto.RankCountResponse> getRankCount() {
        RankDto.RankCountResponse rankCountResponseDto = rankService.countRank();

        return ResponseEntity.ok(rankCountResponseDto);
    }

}
