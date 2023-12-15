package db.project.service;

import db.project.dto.*;
import db.project.exceptions.ErrorCode;
import db.project.exceptions.NoticeException;
import db.project.repository.NoticeRepository;
import db.project.repository.NoticeViewsRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeViewsRepository noticeViewsRepository;

    public NoticeService(NoticeRepository noticeRepository, NoticeViewsRepository noticeViewsRepository) {
        this.noticeRepository = noticeRepository;
        this.noticeViewsRepository = noticeViewsRepository;
    }

    @Transactional
    public NoticeDto.NoticeListResponse noticeList(Optional<Integer> page) {
        int noticePage;
        if(page.isEmpty()) {
            noticePage = 0;
        } else {
            noticePage = (page.get() - 1) * 10;
        }

        Optional<List<NoticeDto.NoticeList>> noticeListDtoOptional = noticeRepository.findNotice(noticePage);
        if(noticeListDtoOptional.isEmpty()) {
            throw new NoticeException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int noticeCount = noticeRepository.findNoticeCount();

        if(noticePage != 0 && noticeCount < noticePage) {
            throw new NoticeException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        List<NoticeDto.NoticeList> noticeListDto = noticeListDtoOptional.get();
        NoticeDto.NoticeListResponse response = new NoticeDto.NoticeListResponse(noticeCount);
        for (NoticeDto.NoticeList notice : noticeListDto) {
            response.getNoticeList().add(notice);
        }

        return response;
    }

    @Transactional
    public NoticeDto.NoticeInfo noticeInfo(int noticeId) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Integer> view_id = noticeViewsRepository.findIdByNoticeAndUser(noticeId, user_id);
        if(view_id.isEmpty()) {
            Optional<Integer> checkPage = noticeViewsRepository.createNoticeViews(noticeId, user_id);
            if(checkPage.isEmpty()) {
                throw new NoticeException("page not post", ErrorCode.NOT_FOUND_POST);
            }
            noticeRepository.updateViewsById(noticeId);
        }

        Optional<NoticeDto.NoticeInfo> noticeInfoDtoOptional = noticeRepository.findNoticeById(noticeId);
        if(noticeInfoDtoOptional.isEmpty()) {
            throw new NoticeException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        NoticeDto.NoticeInfo noticeInfoDto = noticeInfoDtoOptional.get();
        if(user_id.equals(noticeInfoDto.getUser_id())) {
            noticeInfoDto.setAuthor(true);
        } else {
            noticeInfoDto.setAuthor(false);
        }
        return noticeInfoDto;
    }

    public void noticeCreate(NoticeDto.NoticeCreateAndUpdate noticeCreateDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        noticeRepository.createNotice(noticeCreateDto, user_id);
    }

    @Transactional
    public void noticeUpdate(NoticeDto.NoticeCreateAndUpdate noticeUpdateDto, int notice_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = noticeRepository.findUserIdById(notice_id);
        if(userId.isEmpty()) {
            throw new NoticeException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            noticeRepository.updateNoticeById(noticeUpdateDto, notice_id);
        } else {
            throw new NoticeException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }

    @Transactional
    public void noticeDelete(NoticeDto.NoticeDelete noticeDeleteDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = noticeRepository.findUserIdById(noticeDeleteDto.getNotice_id());
        if(userId.isEmpty()) {
            throw new NoticeException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            noticeRepository.deleteNoticeById(noticeDeleteDto.getNotice_id());
        } else {
            throw new NoticeException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }
}
