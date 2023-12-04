package db.project.controller;

import db.project.dto.PostBreakdownReportDto;
import db.project.exceptions.BreakdownReportException;
import db.project.exceptions.ErrorResponse;
import db.project.service.BreakdownReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class BreakdownReportController {  // 고장신고 Controller
    private final BreakdownReportService breakdownReportService;

    public BreakdownReportController(BreakdownReportService breakdownReportService) {
        this.breakdownReportService = breakdownReportService;
    }

    @ResponseBody
    @PostMapping("report")
    @Operation(
            summary = "고장신고",
            description = "내용을 입력하고 고장신고 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "접수 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            }),
            @ApiResponse(responseCode = "400", description = "접수 실패 또는 자전거 상태 업데이트 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 고장신고
    public ResponseEntity<String> report(@RequestBody PostBreakdownReportDto form){
        try{
            String result = breakdownReportService.report(form);
            return ResponseEntity.ok("{}");
        } catch (BreakdownReportException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
