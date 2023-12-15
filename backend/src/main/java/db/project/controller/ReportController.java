package db.project.controller;

import db.project.dto.BreakdownReportDto;
import db.project.exceptions.BreakdownReportException;
import db.project.exceptions.ErrorResponse;
import db.project.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ReportController {  // 고장신고 Controller
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
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
    public ResponseEntity<String> postReport(@RequestBody BreakdownReportDto.Report form){
        try{
            String result = reportService.report(form);
            return ResponseEntity.ok("{}");
        } catch (BreakdownReportException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping({"/admin/report/list/{page}", "/admin/report/list"})
    @Operation(
            summary = "고장신고 리스트",
            description = "고장신고 관리 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고장신고 리스트 열람 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    // 고장신고 List
    public ResponseEntity<BreakdownReportDto.BreakdownReportListResponse> getReportList(@PathVariable(required = false) Optional<Integer> page) {
        return ResponseEntity.ok(reportService.reportList(page));
    }

    @ResponseBody
    @PostMapping("/admin/report/repair")
    @Operation(
            summary = "고장신고 수리",
            description = "고장신고 리스트에서 수리 버튼을 클릭했을 때의 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고장신고 수리 성공", content = {
                    @Content(examples = {
                            @ExampleObject(value = "{}")})
            })
    })
    public ResponseEntity<String> postReportRepair(@RequestBody BreakdownReportDto.BreakdownReportRepair form) {
        reportService.reportRepair(form);

        return ResponseEntity.ok("{}");
    }
}
