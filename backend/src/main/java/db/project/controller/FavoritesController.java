package db.project.controller;

import db.project.dto.FavoritesResponseDto;
import db.project.dto.PostFavoritesChangeDto;
import db.project.dto.PostFavoritesSearchDto;
import db.project.service.FavoritesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class FavoritesController {  // 즐겨찾기 controller
    private final FavoritesService favoritesService;

    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    @PostMapping("favorites/list")
    @ResponseBody
    @Operation(
            summary = "이름과 일치하는 대여소 검색",
            description = "즐겨찾기 할 대여소를 검색한 후에 찾기 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검색 지역과 일치하는 대여소 검색 성공")
    })
    // 검색 지역과 일치하는 대여소
    public ResponseEntity<FavoritesResponseDto> postFavoriteList(@RequestBody PostFavoritesSearchDto form) {
        FavoritesResponseDto favoritesResponseDto = favoritesService.locationList(form);

        return ResponseEntity.ok(favoritesResponseDto);
    }

    @PostMapping("favorites/change")
    @ResponseBody
    @Operation(
            summary = "즐겨찾기 추가 또는 삭제",
            description = "즐겨찾기 버튼을 클릭하거나 해제 했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즐겨찾기 변경 성공", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "즐겨찾기 추가 성공", name = "FavoritesAdd"),
                            @ExampleObject(value = "즐겨찾기 삭제 성공", name = "FavoritesDelete")
                    })
            }),
            @ApiResponse(responseCode = "500", description = "즐겨찾기 변경 실패", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "이미 즐겨찾기에 추가된 대여소 입니다.")
                    })
            })
    })
    // 즐겨찾기 추가 또는 삭제
    public ResponseEntity<String> postFavoritesChange(@RequestBody PostFavoritesChangeDto form) {
        return ResponseEntity.ok(favoritesService.locationChange(form));
    }

    @GetMapping("favorites/list")
    @ResponseBody
    @Operation(
            summary = "즐겨찾기한 대여소 검색",
            description = "대여소 즐겨찾기 페이지에 들어갔을 때의 API, 해당 사용자가 즐겨찾기한 대여소"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즐겨찾기된 대여소 검색 성공")
    })
    // 즐겨찾기한 대여소
    public ResponseEntity<FavoritesResponseDto> getFavoritesList() {
        FavoritesResponseDto favoritesResponseDto = favoritesService.locationList();

        return ResponseEntity.ok(favoritesResponseDto);
    }
}
