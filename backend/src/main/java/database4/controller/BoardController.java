package database4.controller;

import database4.dto.BoardListResponseDto;
import database4.dto.PostBoardCreateAndUpdateDto;
import database4.exceptions.BoardException;
import database4.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class BoardController {  // 게시판 Controller
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("board/list")
    @ResponseBody
    @Operation(
            summary = "자유게시판 리스트",
            description = "자유게시판 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 리스트 열람 성공"),
            @ApiResponse(responseCode = "400", description = "게시물 리스트 열람 실패")
    })
    // 게시물 리스트
    public ResponseEntity<BoardListResponseDto> getBoardList(@RequestParam(defaultValue = "1") int page) {

        try{
            return ResponseEntity.ok(boardService.boardList(page));
        } catch (BoardException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("board/info/{boardId}")
    @ResponseBody
    @Operation(
            summary = "게시물 상세정보",
            description = "게시물을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 열람 성공"),
            @ApiResponse(responseCode = "400", description = "게시물 열람 실패", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "존재하지 않는 게시물 입니다.")
                    })
            })
    })
    // 게시물 상세정보
    public ResponseEntity<?> getBoardInfo(@PathVariable int boardId) {
        try{
            return ResponseEntity.ok(boardService.boardInfo(boardId));
        } catch (BoardException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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
    public ResponseEntity<Void> postBoardCreate(@RequestBody PostBoardCreateAndUpdateDto form) {
        boardService.boardCreate(form);
        return ResponseEntity.ok().build();
    }

    @PostMapping("board/update/{boardId}")
    @ResponseBody
    @Operation(
            summary = "게시물 수정",
            description = "게시물 제목과 본문을 입력하고 수정 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 업데이트 성공", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "게시물이 업데이트 되었습니다.")})
            }),
            @ApiResponse(responseCode = "400", description = "게시물 업데이트 실패", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "존재하지 않는 게시물 입니다.", name = "NonExist"),
                            @ExampleObject(value = "게시물 업데이트 실패", name = "UpdateFailed"),
                            @ExampleObject(value = "게시물의 작성자가 아닙니다.", name = "MismatchAuthor")
                    })
            })
    })
    // 게시물 수정
    public ResponseEntity<String> postBoardUpdate(@PathVariable int boardId, @RequestBody PostBoardCreateAndUpdateDto form) {

        try {
            String result = boardService.boardUpdate(form, boardId);
            return ResponseEntity.ok(result);
        } catch (BoardException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("board/delete/{boardId}")
    @ResponseBody
    @Operation(
            summary = "게시물 삭제",
            description = "게시물 상세정보에서 삭제 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 삭제 성공", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "게시물이 삭제되었습니다.")})
            }),
            @ApiResponse(responseCode = "400", description = "게시물 삭제 실패", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "존재하지 않는 게시물 입니다.", name = "NonExist"),
                            @ExampleObject(value = "게시물 삭제 실패", name = "DeleteFailed"),
                            @ExampleObject(value = "게시물의 작성자가 아닙니다.", name = "MismatchAuthor")
                    })
            })
    })
    // 게시물 삭제
    public ResponseEntity<String> postBoardDelete(@PathVariable int boardId) {
        try{
            String result = boardService.boardDelete(boardId);
            return ResponseEntity.ok(result);
        } catch (BoardException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
