package db.project.service;

import db.project.dto.*;
import db.project.exceptions.BreakdownReportException;
import db.project.exceptions.ErrorCode;
import db.project.repository.BikeRepository;
import db.project.repository.ReportRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final BikeRepository bikeRepository;

    public ReportService(ReportRepository reportRepository, BikeRepository bikeRepository) {
        this.reportRepository = reportRepository;
        this.bikeRepository = bikeRepository;
    }

    @Transactional
    public String report(BreakdownReportDto.Report breakdownReportDto){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        reportRepository.createReport(breakdownReportDto, user_id)
                .orElseThrow(() -> new BreakdownReportException("FAILURE TO RECEIVE FAULT REPORT", ErrorCode.FAIL_REPORT));

        bikeRepository.updateStatusClosedById(breakdownReportDto.getBike_id())
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

        Optional<List<BreakdownReportDto.BreakdownReportList>> reportListDtoOptional = reportRepository.findReportByStatus(reportPage);
        if(reportListDtoOptional.isEmpty()) {
            throw new BreakdownReportException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int reportCount = reportRepository.findReportCountByStatus();

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
        reportRepository.updateStatusByStatusAndBike(breakdownReportRepairDto);
    }
}
