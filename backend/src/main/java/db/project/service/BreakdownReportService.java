package db.project.service;

import db.project.dto.PostBreakdownReportDto;
import db.project.exceptions.BreakdownReportException;
import db.project.exceptions.ErrorCode;
import db.project.repository.BreakdownReportRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BreakdownReportService {
    private final BreakdownReportRepository breakdownReportRepository;

    public BreakdownReportService(BreakdownReportRepository breakdownReportRepository) {
        this.breakdownReportRepository = breakdownReportRepository;
    }

    @Transactional
    public String report(PostBreakdownReportDto postBreakdownReportDto){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        breakdownReportRepository.insertReport(postBreakdownReportDto, user_id)
                .orElseThrow(() -> new BreakdownReportException("FAILURE TO RECEIVE FAULT REPORT", ErrorCode.FAIL_REPORT));

        breakdownReportRepository.updateBikeStatus(postBreakdownReportDto.getBike_id())
                .orElseThrow(() -> new BreakdownReportException("BIKE STATUS UPDATE FAILED", ErrorCode.FAIL_REPORT));

        return "고장신고가 접수되었습니다.";

    }
}
