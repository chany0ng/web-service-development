package database4.service;

import database4.dto.PostBreakdownReportDto;
import database4.exceptions.BreakdownReportException;
import database4.repository.BreakdownReportRepository;
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
                .orElseThrow(() -> new BreakdownReportException("고장신고 접수 실패"));

        breakdownReportRepository.updateBikeStatus(postBreakdownReportDto.getBike_id())
                .orElseThrow(() -> new BreakdownReportException("자전거 상태 업데이트 실패"));

        return "고장신고가 접수되었습니다.";

    }
}
