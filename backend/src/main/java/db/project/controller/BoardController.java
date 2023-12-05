package db.project.controller;

import db.project.dto.BoardListResponseDto;
import db.project.dto.PostBoardCreateAndUpdateDto;
import db.project.dto.ReturnGetBoardInfoDto;
import db.project.exceptions.ErrorResponse;
import db.project.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class BoardController {  // 게시판 Controller
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("board/list/{page}")
    @ResponseBody
    @Operation(
            summary = "자유게시판 리스트",
            description = "자유게시판 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 리스트 열람 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 게시물 리스트
    public ResponseEntity<BoardListResponseDto> getBoardList(@PathVariable int page) {

        return ResponseEntity.ok(boardService.boardList(page));
    }

    @GetMapping("board/info/{boardId}")
    @ResponseBody
    @Operation(
            summary = "게시물 상세정보",
            description = "게시물을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 열람 성공"),
            @ApiResponse(responseCode = "404", description = "게시물을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 게시물 상세정보
    public ResponseEntity<ReturnGetBoardInfoDto> getBoardInfo(@PathVariable int boardId) {

        return ResponseEntity.ok(boardService.boardInfo(boardId));
    }

    @PostMapping("board/create")
    @ResponseBody
    @Operation(
            summary = "게시물 생성",
            description = "게시물 제목과 본문을 입력하고 생성 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 생성 성공")
    })
    // 게시물 생성
    public ResponseEntity<String> postBoardCreate(@RequestBody PostBoardCreateAndUpdateDto form) {
        boardService.boardCreate(form);

        return ResponseEntity.ok("{}");
    }

    @PostMapping("board/update/{boardId}")
    @ResponseBody
    @Operation(
            summary = "게시물 수정",
            description = "게시물 제목과 본문을 입력하고 수정 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 업데이트 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            }),
            @ApiResponse(responseCode = "403", description = "게시물 저자가 아님", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 게시물 수정
    public ResponseEntity<String> postBoardUpdate(@PathVariable int boardId, @RequestBody PostBoardCreateAndUpdateDto form) {
        boardService.boardUpdate(form, boardId);

        return ResponseEntity.ok("{}");

    }

    @PostMapping("board/delete/{boardId}")
    @ResponseBody
    @Operation(
            summary = "게시물 삭제",
            description = "게시물 상세정보에서 삭제 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 삭제 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            }),
            @ApiResponse(responseCode = "403", description = "게시물 저자가 아님", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 게시물 삭제
    public ResponseEntity<String> postBoardDelete(@PathVariable int boardId) {
        boardService.boardDelete(boardId);

        return ResponseEntity.ok("{}");

    }
}
