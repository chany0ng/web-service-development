package db.project.service;

import db.project.dto.*;
import db.project.exceptions.ErrorCode;
import db.project.exceptions.NoticeException;
import db.project.repository.NoticeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Transactional
    public NoticeListResponseDto noticeList(Optional<Integer> page) {
        int noticePage;
        if(page.isEmpty()) {
            noticePage = 0;
        } else {
            noticePage = (page.get() - 1) * 10;
        }

        Optional<List<ReturnGetNoticeListDto>> noticeListOptional = noticeRepository.noticeList(noticePage);
        if(noticeListOptional.isEmpty()) {
            throw new NoticeException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int noticeCount = noticeRepository.getNoticeCount();

        if(noticePage != 0 && noticeCount < noticePage) {
            throw new NoticeException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        List<ReturnGetNoticeListDto> noticeList = noticeListOptional.get();
        NoticeListResponseDto response = new NoticeListResponseDto(noticeCount);
        for (ReturnGetNoticeListDto notice : noticeList) {
            response.getNoticeList().add(notice);
        }

        return response;
    }

    @Transactional
    public ReturnGetNoticeInfoDto noticeInfo(int noticeId) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Integer> view_id = noticeRepository.getAdminIdAndNoticeId(noticeId, user_id);
        if(view_id.isEmpty()) {
            Optional<Integer> checkPage = noticeRepository.insertNoticeViews(noticeId, user_id);
            if(checkPage.isEmpty()) {
                throw new NoticeException("page not post", ErrorCode.NOT_FOUND_POST);
            }
            noticeRepository.updateNoticeView(noticeId);
        }

        Optional<ReturnGetNoticeInfoDto> returnGetNoticeInfoDtoOptional = noticeRepository.noticeInfo(noticeId);
        if(returnGetNoticeInfoDtoOptional.isEmpty()) {
            throw new NoticeException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        ReturnGetNoticeInfoDto returnGetNoticeInfoDto = returnGetNoticeInfoDtoOptional.get();
        if(user_id.equals(returnGetNoticeInfoDto.getUser_id())) {
            returnGetNoticeInfoDto.setAuthor(true);
        } else {
            returnGetNoticeInfoDto.setAuthor(false);
        }
        return returnGetNoticeInfoDto;
    }

    public void noticeCreate(PostBoardAndNoticeCreateAndUpdateDto noticeCreateDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        noticeRepository.noticeCreate(noticeCreateDto, user_id);
    }

    @Transactional
    public void noticeUpdate(PostBoardAndNoticeCreateAndUpdateDto postNoticeUpdateDto, int notice_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = noticeRepository.isAuthor(notice_id);
        if(userId.isEmpty()) {
            throw new NoticeException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            noticeRepository.noticeUpdate(postNoticeUpdateDto, notice_id);
        } else {
            throw new NoticeException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }

    @Transactional
    public void noticeDelete(PostBoardAndNoticeDeleteDto postBoardAndNoticeDeleteDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = noticeRepository.isAuthor(postBoardAndNoticeDeleteDto.getId());
        if(userId.isEmpty()) {
            throw new NoticeException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            noticeRepository.noticeDelete(postBoardAndNoticeDeleteDto.getId());
        } else {
            throw new NoticeException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }
}
