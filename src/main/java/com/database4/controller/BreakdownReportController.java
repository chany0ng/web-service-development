package com.database4.controller;

import com.database4.dto.PostBreakdownReportDto;
import com.database4.exceptions.BreakdownReportException;
import com.database4.service.BreakdownReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "고장신고가 접수되었습니다.")})
            }),
            @ApiResponse(responseCode = "400", description = "접수 실패", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "고장신고 접수 실패", name = "ReportFailed"),
                            @ExampleObject(value = "자전거 상태 업데이트 실패", name = "BikeStatusUpdateFailed")
                    })
            })
    })
    // 고장신고
    public ResponseEntity<String> report(@RequestBody PostBreakdownReportDto form){
        try{
            String result = breakdownReportService.report(form);
            return ResponseEntity.ok(result);
        } catch (BreakdownReportException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
