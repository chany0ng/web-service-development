package db.project.controller;

import db.project.dto.NoticeListResponseDto;
import db.project.exceptions.NoticeException;
import db.project.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api")
public class NoticeController {  // 공지사항 Controller
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("notice/list")
    @ResponseBody
    @Operation(
            summary = "공지사항 리스트",
            description = "공지사항 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 리스트 열람 성공"),
            @ApiResponse(responseCode = "400", description = "공지사항 리스트 열람 실패")
    })
    // 공지사항 리스트
    public ResponseEntity<NoticeListResponseDto> getNoticeList(@RequestParam(defaultValue = "1") int page) {
        try{
            return ResponseEntity.ok(noticeService.noticeList(page));
        } catch (NoticeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("notice/info/{noticeId}")
    @ResponseBody
    @Operation(
            summary = "공지사항 상세정보",
            description = "공지사항을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 열람 성공"),
            @ApiResponse(responseCode = "400", description = "공지사항 열람 실패", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "존재하지 않는 게시물 입니다.")
                    })
            })
    })
    // 공지사항 상세정보
    public ResponseEntity<?> getNoticeInfo(@PathVariable int noticeId) {
        try{
            return ResponseEntity.ok(noticeService.noticeInfo(noticeId));
        } catch (NoticeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
