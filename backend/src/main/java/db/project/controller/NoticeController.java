package db.project.controller;

import db.project.dto.NoticeListResponseDto;
import db.project.dto.ReturnGetNoticeInfoDto;
import db.project.exceptions.ErrorResponse;
import db.project.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class NoticeController {  // 공지사항 Controller
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("notice/list/{page}")
    @ResponseBody
    @Operation(
            summary = "공지사항 리스트",
            description = "공지사항 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 리스트 열람 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 공지사항 리스트
    public ResponseEntity<NoticeListResponseDto> getNoticeList(@PathVariable int page) {

        return ResponseEntity.ok(noticeService.noticeList(page));
    }

    @GetMapping("notice/info/{noticeId}")
    @ResponseBody
    @Operation(
            summary = "공지사항 상세정보",
            description = "공지사항을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 열람 성공"),
            @ApiResponse(responseCode = "404", description = "게시물을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 공지사항 상세정보
    public ResponseEntity<ReturnGetNoticeInfoDto> getNoticeInfo(@PathVariable int noticeId) {

        return ResponseEntity.ok(noticeService.noticeInfo(noticeId));
    }
}
