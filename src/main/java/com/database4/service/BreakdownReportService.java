package com.database4.service;

import com.database4.dto.PostBreakdownReportDto;
import com.database4.exceptions.BreakdownReportException;
import com.database4.repository.BreakdownReportRepository;
import org.springframework.stereotype.Service;

@Service
public class BreakdownReportService {
    private final BreakdownReportRepository breakdownReportRepository;

    public BreakdownReportService(BreakdownReportRepository breakdownReportRepository) {
        this.breakdownReportRepository = breakdownReportRepository;
    }

    public String report(PostBreakdownReportDto postBreakdownReportDto){
        try{
            return breakdownReportRepository.report(postBreakdownReportDto)
                    .orElseThrow(() -> new BreakdownReportException("고장신고 접수 실패"));
        } catch (BreakdownReportException e) {
            throw e;
        }
    }
}
