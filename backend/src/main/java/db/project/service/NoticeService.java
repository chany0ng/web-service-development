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
    public BoardAndNoticeListResponseDto noticeList(int page) {
        page = (page - 1) * 10;
        Optional<List<ReturnGetBoardAndNoticeListDto>> noticeListOptional = noticeRepository.noticeList(page);
        if(noticeListOptional.isEmpty()) {
            throw new NoticeException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int noticeCount = noticeRepository.getNoticeCount();

        if(page != 0 && noticeCount < page) {
            throw new NoticeException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        List<ReturnGetBoardAndNoticeListDto> noticeList = noticeListOptional.get();
        BoardAndNoticeListResponseDto response = new BoardAndNoticeListResponseDto(noticeCount);
        for (ReturnGetBoardAndNoticeListDto notice : noticeList) {
            response.getBoardAndNoticeList().add(notice);
        }

        return response;
    }

    public ReturnGetBoardAndNoticeInfoDto noticeInfo(int noticeId) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ReturnGetBoardAndNoticeInfoDto> returnGetNoticeInfoDtoOptional = noticeRepository.noticeInfo(noticeId - 1);
        if(returnGetNoticeInfoDtoOptional.isEmpty()) {
            throw new NoticeException("page not post", ErrorCode.NOT_FOUND_POST);
        }
        ReturnGetBoardAndNoticeInfoDto returnGetNoticeInfoDto = returnGetNoticeInfoDtoOptional.get();
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
        Optional<Integer> noticeId = noticeRepository.getNoticeId(notice_id - 1);
        if(noticeId.isEmpty()) {
            throw new NoticeException("page not post", ErrorCode.NOT_FOUND_POST);
        }
        String userId = noticeRepository.isAuthor(noticeId.get());
        if(user_id.equals(userId)) {
            noticeRepository.noticeUpdate(postNoticeUpdateDto, noticeId.get());
        } else {
            throw new NoticeException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }

    @Transactional
    public void noticeDelete(int notice_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Integer> noticeId = noticeRepository.getNoticeId(notice_id - 1);
        if(noticeId.isEmpty()) {
            throw new NoticeException("page not post", ErrorCode.NOT_FOUND_POST);
        }
        String userId = noticeRepository.isAuthor(noticeId.get());
        if(user_id.equals(userId)) {
            noticeRepository.noticeDelete(noticeId.get());
        } else {
            throw new NoticeException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }
}
