package db.project.controller;

import db.project.dto.BoardCommentDto;
import db.project.exceptions.ErrorResponse;
import db.project.service.BoardCommentService;
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
public class BoardCommentController {  // 게시판 댓글 Controller
    private final BoardCommentService boardCommentService;

    public BoardCommentController(BoardCommentService boardCommentService) {
        this.boardCommentService = boardCommentService;
    }

    @PostMapping("board/comment/create/{boardId}")
    @ResponseBody
    @Operation(
            summary = "게시물 댓글 생성",
            description = "댓글 본문을 입력하고 생성 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 댓글 생성 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            })
    })
    public ResponseEntity<String> postCreateComment(@PathVariable int boardId, @RequestBody BoardCommentDto.BoardCommentCreateAndUpdate form) {
        boardCommentService.createComment(form, boardId);

        return ResponseEntity.ok("{}");
    }

    @PostMapping("board/comment/delete")
    @ResponseBody
    @Operation(
            summary = "게시물 댓글 삭제",
            description = "게시물 댓글 삭제 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 댓글 삭제 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            }),
            @ApiResponse(responseCode = "403", description = "댓글 저자가 아님", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<String> postDeleteComment(@RequestBody BoardCommentDto.BoardCommentDelete form) {
        boardCommentService.deleteComment(form);

        return ResponseEntity.ok("{}");
    }

    @PostMapping("board/comment/update/{commentId}")
    @ResponseBody
    @Operation(
            summary = "게시물 댓글 수정",
            description = "댓글 본문을 입력하고 수정 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 댓글 업데이트 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            }),
            @ApiResponse(responseCode = "403", description = "댓글 저자가 아님", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<String> postUpdateComment(@PathVariable int commentId, @RequestBody BoardCommentDto.BoardCommentCreateAndUpdate form) {
        boardCommentService.updateComment(form, commentId);

        return ResponseEntity.ok("{}");
    }
}
