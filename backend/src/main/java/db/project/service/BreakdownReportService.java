package db.project.service;

import db.project.dto.*;
import db.project.exceptions.BreakdownReportException;
import db.project.exceptions.ErrorCode;
import db.project.repository.BreakdownReportRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BreakdownReportService {
    private final BreakdownReportRepository breakdownReportRepository;

    public BreakdownReportService(BreakdownReportRepository breakdownReportRepository) {
        this.breakdownReportRepository = breakdownReportRepository;
    }

    @Transactional
    public String report(BreakdownReportDto.Report breakdownReportDto){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        breakdownReportRepository.insertReport(breakdownReportDto, user_id)
                .orElseThrow(() -> new BreakdownReportException("FAILURE TO RECEIVE FAULT REPORT", ErrorCode.FAIL_REPORT));

        breakdownReportRepository.updateBikeStatus(breakdownReportDto.getBike_id())
                .orElseThrow(() -> new BreakdownReportException("BIKE STATUS UPDATE FAILED", ErrorCode.FAIL_REPORT));

        return "고장신고가 접수되었습니다.";
    }

    @Transactional
    public BreakdownReportDto.BreakdownReportListResponse reportList(Optional<Integer> page) {
        int reportPage;
        if(page.isEmpty()) {
            reportPage = 0;
        } else {
            reportPage = (page.get() - 1) * 10;
        }

        Optional<List<BreakdownReportDto.BreakdownReportList>> reportListDtoOptional = breakdownReportRepository.reportList(reportPage);
        if(reportListDtoOptional.isEmpty()) {
            throw new BreakdownReportException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int reportCount = breakdownReportRepository.getReportCount();

        if(reportPage != 0 && reportCount <= reportPage) {
            throw new BreakdownReportException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        List<BreakdownReportDto.BreakdownReportList> reportListDto = reportListDtoOptional.get();
        BreakdownReportDto.BreakdownReportListResponse response = new BreakdownReportDto.BreakdownReportListResponse(reportCount);
        for (BreakdownReportDto.BreakdownReportList report : reportListDto) {
            response.getReportList().add(report);
        }
        return response;
    }

    public void reportRepair(BreakdownReportDto.BreakdownReportRepair breakdownReportRepairDto) {
        breakdownReportRepository.updateReportStatus(breakdownReportRepairDto);
    }
}
