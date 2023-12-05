package db.project.service;

import db.project.dto.NoticeListResponseDto;
import db.project.dto.ReturnGetNoticeInfoDto;
import db.project.dto.ReturnGetNoticeListDto;
import db.project.exceptions.NoticeException;
import db.project.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public NoticeListResponseDto noticeList(int page) {
        page = (page - 1) * 10;
        Optional<List<ReturnGetNoticeListDto>> noticeListOptional = noticeRepository.noticeList(page);
        if(noticeListOptional.isEmpty()) {
            throw new NoticeException("잘못된 페이지 접근입니다.");
        }
        List<ReturnGetNoticeListDto> noticeList = noticeListOptional.get();
        NoticeListResponseDto response = new NoticeListResponseDto();
        for (ReturnGetNoticeListDto notice : noticeList) {
            response.getNoticeList().add(notice);
        }

        return response;
    }

    public ReturnGetNoticeInfoDto noticeInfo(int noticeId) {
        return noticeRepository.noticeInfo(noticeId - 1)
                .orElseThrow(() -> new NoticeException("존재하지 않는 게시물 입니다."));
    }
}
