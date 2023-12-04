package dp.project.controller;

import dp.project.dto.RankCountResponseDto;
import dp.project.dto.RankTimeResponseDto;
import dp.project.service.RankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<RankTimeResponseDto> getRankTime() {
        RankTimeResponseDto returnGetRankTimeDto = rankService.timeRank();

        return ResponseEntity.ok(returnGetRankTimeDto);
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
    public ResponseEntity<RankCountResponseDto> getRankCount() {
        RankCountResponseDto rankCountResponseDto = rankService.countRank();

        return ResponseEntity.ok(rankCountResponseDto);
    }

}
